```
[default]
version=1.1
provider=ovs # ovs or linux

[bond]

[ovs]
vlan_splinters=off
datamapping=[physnet0:br-phy0:enp129s0f1,physnet1:br-phy1:enp129s0f0]
admin=enp129s0f0 # configured with 88.88.1.*
papi=enp129s0f0  # from physnet1, can communicate with public network

pmgmt=enp129s0f0  # interface with publish ip
iapi=enp129s0f1   # interface with public temporary ip
```