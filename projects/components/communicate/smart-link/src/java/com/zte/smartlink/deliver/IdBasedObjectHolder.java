/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.smartlink.deliver;

import java.io.File;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Walker;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 17-1-5.
 */
public class IdBasedObjectHolder<T extends IdBasedObject> {
    public IdBasedObjectHolder(final String name, final Class<T> clazz) {
        db = new BerkeleyDB<>(
                indexer = new BerkeleyDBObjectIndexer<Long, T>() {
                    private Environment environment = null;

                    @Override
                    public void createDB(Environment env) {
                        dbName = name;
                        environment = createEnvironmentBy(env);

                        synchronized (lock) {
                            if (catalogDb == null) {
                                catalogDb = environment.openDatabase(
                                        null,
                                        "MsgHolder_catalogDb",
                                        createCatalogConfig());
                                catalog = new StoredClassCatalog(catalogDb);
                            }
                        }

                        dataBinding = new SerialBinding<>(catalog, clazz);

                        dbConfig = new DatabaseConfig();
                        dbConfig.setAllowCreate(true);
                        dbConfig.setSortedDuplicates(false);
                        dbConfig.setDeferredWrite(true);
                        dbConfig.setBtreeComparator(new LongComparator());

                        db = environment.openDatabase(null, dbName, dbConfig);
                    }

                    private Environment createEnvironmentBy(final Environment env) {
                        EnvironmentConfig config = env.getConfig().clone();
                        final File file = new File(env.getHome().getAbsolutePath() + File.separator
                                                           + IdBasedObjectHolder.class.getSimpleName() + "_"
                                                           + clazz.getSimpleName());
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        return new Environment(file, config);
                    }

                    @Override
                    public void stop() {
                        db.close();
                        env.removeDatabase(null, dbName);
                    }

                    @Override
                    public Database getDatabase() {
                        return db;
                    }

                    @Override
                    public void sync() {
                        super.sync();
                        catalogDb.sync();
                    }

                    @Override
                    protected String toKeyString(T data) {
                        return String.valueOf(data.id());
                    }
                });
    }

    final static DatabaseConfig createCatalogConfig() {
        DatabaseConfig catalogConfig = new DatabaseConfig();
        catalogConfig.setAllowCreate(true);
        catalogConfig.setDeferredWrite(true);
        return catalogConfig;
    }

    static final Object lock = new Object();
    static StoredClassCatalog catalog = null;
    static Database catalogDb = null;
    private final BerkeleyDBObjectIndexer<Long, T> indexer;
    private BerkeleyDB<Long, T> db;

    public T poll(long key) {
        return indexer.get(key);
    }

    public void put(T t) throws MOSException {
        db.put(t);
        logger(getClass()).test("putting " + t);
        indexer.sync();
    }

    public void clear() {
        db.clearAll();
    }

    public void all(Walker<Long, T> walker) throws BerkeleyDBException {
        indexer.all(walker);
    }

    public void remove(long key) throws BerkeleyDBException {
        indexer.remove(key);
        logger(getClass()).test("removing " + key);
        indexer.sync();
    }
}
