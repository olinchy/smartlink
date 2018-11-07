#!/bin/bash

ip_group=`echo $ip_group|tr ',' ' '`

echo ">> get cluster info..."
for ip in $ip_group
do
    num=`echo cluster nodes | $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE' | wc -l`
    if (( $num>1 ));
    then
      cmd_str="echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'"
      bad_nodes=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/:0/'|awk  '{print $1}'`
      echo "bad_nodes:"$bad_nodes

      master_nodes=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/ connected/'|awk '/master/'|awk '{print $1}'`
      echo "master_nodes: "$master_nodes

      slave_of_nodes=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/ connected/'|awk '/slave/'|awk '{print $4}'`
      echo "slave_of_nodes: "$slave_of_nodes
	  
	  con_slave_ips=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/ connected/'|awk '/slave/'|awk '{print $2}'`
	  echo "con_slave_ips: "$con_slave_ips
	  
      unfrag_slots=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/disconnected/'|awk '/master/'|awk '{print $9}'`
      echo "unfrag_slots: "$unfrag_slots

      con_ips=`echo cluster nodes| $HOME/bin/redis-cli -h ${ip} -p 6379 -a 'db10$ZTE'|awk '/ connected/'|awk '{print $2}'`
      echo "con_ips: "$con_ips

      break
    fi
done

#reassgin master to slave whose master has been down
i=1
echo ">> reassgin master..."
for node in $slave_of_nodes
do
  if [[ $master_nodes =~ $node ]]
  then
    echo "node "$node" has healthy master!"
	i=$i+1
  else
	slave_ip= `echo $con_slave_ips | cut -d ' ' -f $i`
    echo "reassgin master for "$node", ip"$slave_ip
	
    `echo cluster replicate ${master_nodes[0]} | $HOME/bin/redis-cli -h ${slave_ip%:*} -p 6379 -a 'db10$ZTE' > /dev/null`
	i=$i+1
  fi
done

#forget_cluster_nodes
echo ">> cluster forget..."
for ip in $con_ips
do
  echo "ip:"${ip%:*}
  for node in $bad_nodes
  do
    echo "forget node:"$node
    `echo cluster forget $node | $HOME/bin/redis-cli -h ${ip%:*} -p 6379 -a 'db10$ZTE' >/dev/null `
  done
done

#add_new_nodes
echo ">> cluster meet..."
declare -a new_ips_arr
for ip in $ip_group
do
  if [[ $con_ips =~ $ip ]]
  then
    echo "">/dev/null
  else
    echo "new node ip:"${ip%:*}
    new_ips_arr=(${new_ips_arr[@]} ${ip%:*})
    `echo cluster meet ${ip%:*} 6379 | $HOME/bin/redis-cli -h ${con_ips%%:*} -p 6379 -a 'db10$ZTE' > /dev/null` 
  fi
done

#allocate_slots

echo ">> cluster add slots..."
unfrag_slots_arr=($unfrag_slots)
for i in ${!unfrag_slots_arr[@]}
do
  low=${unfrag_slots_arr[$i]%-*}
  high=${unfrag_slots_arr[$i]#*-}
  echo "low:"$low",high:"$high
  for ((j=$low;j<=$high;j++))
    do
        `$HOME/bin/redis-cli -h ${new_ips_arr[$i]} -p 6379 -a 'db10$ZTE' cluster addslots $j > /dev/null`
    done
  echo "unset ip:"${new_ips_arr[$i]}
  unset new_ips_arr[$i]
done

#cluster replicate

echo ">> cluster replicate..."
tmp_master=`echo $master_nodes|cut -d ' ' -f 1`
sleep 1
for ip in ${new_ips_arr[@]}
do
  echo "cluster replicate for "$ip
  `echo cluster replicate $tmp_master| $HOME/bin/redis-cli -h $ip -p 6379 -a 'db10$ZTE' > /dev/null` 
done

