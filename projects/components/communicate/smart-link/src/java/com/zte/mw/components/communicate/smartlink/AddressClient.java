package com.zte.mw.components.communicate.smartlink;

import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.Address;
import com.zte.mw.components.communicate.smartlink.model.message.AddressSyncMsg;

public class AddressClient implements Address<AddressSyncMsg> {
    private AddressBook addressBook = new AddressBook();

    @Override
    public void on(final AddressSyncMsg message) {
        message.update(this.addressBook);
    }
}
