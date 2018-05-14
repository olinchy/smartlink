### download package
### unzip 

### execute install command
- install all in one click

```
cd [unzip_path]/otcp-v[version]/oki-tools/tools/bin
chmod a+x oki-cli
# disable gbase
./oki-cli component set --gbase=false

./oki-cli install -m all -t otcp
```
- install step by step
```
./oki-cli component set --gbase=false
./oki-cli install -m steps -t otcp

``` 
