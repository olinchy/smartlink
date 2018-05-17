1. bridge_mappings = physnet0:br-phy0,physnet1:br-phy1
    ```
    1. br-phy created by install process
    2. count(physnet) == count(physical network interface) 
    ```
2. keep it default 
    ```
    type_drivers = vlan,flat
    tenant_network_types = vlan
    flat_networks = physnet0  // flat network can only be physnet0, do not know why
    external_network_bridge = br-iapi
    l3agent_type=neutron
    ```
3. network_vlan_ranges = physnet0:1000:1010,physnet1:1011:1020
    ```
    1. inner transmission vlan
    2. stick to bridge_mappings
    3. according to wiki, same vlan section was suggested, but why?
    ```
