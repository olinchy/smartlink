pkgver=v1.17.30.03.p10
branch=r6_bugfix
mver=$(wget -q -O- ftp://zxpaas:zxpaas@10.67.18.8/branch_version | grep -E '^'$branch' ' | awk '{print $2}')

cd /tmp
wget ftp://zxpaas:zxpaas@10.67.18.8/zartcli -Nnd -q
chmod +x zartcli

declare -A temp_ipport=(
["sh_temp_srepo"]=$(curl -s http://10.67.18.170/shell/get_zart_info | xargs -0 -I {} bash -c "branch_name="$branch";info=registry;{}")
)
declare -A temp_zart_ipport=(
["sh_temp_srepo"]=$(curl -s http://10.67.18.170/shell/get_zart_info | xargs -0 -I {} bash -c "branch_name="$branch";info=zart;{}")
)

declare -A valid_ipport=(
["cd_valid_srepo"]="10.74.148.217:7777"
["xa_valid_srepo"]="10.92.253.239:7777"
["sh_valid_srepo"]="10.62.107.171:62648"
)
declare -A valid_zart_ipport=(
["cd_valid_srepo"]="10.74.148.217:6000"
["xa_valid_srepo"]="10.92.253.239:6000"
["sh_valid_srepo"]="10.62.107.171:52275"
)

declare -A release_ipport=(
["cd"]="10.74.148.221:33377"
["xa"]="10.92.253.238:7777"
["sh"]="10.67.18.253:37777"
)
declare -A release_zart_ipport=(
["cd"]="10.74.148.221:6000"
["xa"]="10.92.253.238:6000"
["sh"]="10.67.18.253:6000"
)

unset ipport
declare -A ipport
getsrepo()
{
    declare -A ipport
    eval "declare -A ipport="${1#*=}
    for key in ${!ipport[@]}; 
    do 
        ms=$(ping -c 3 -W 2 -i 0 ${ipport[$key]%:*}|grep rtt);
        if [ ! -z "$ms" ]; then
            echo $ms $key;
        fi
    done|tr '/' ' '|sort -n -t ' ' -k 8|sed 's/.* //g'|head -n 1
}

case $1 in
*'temp_srepo')
    pkgver=$mver
    echo "searhing temp_srepo ......"
    fastest_srepo=$(getsrepo "$(declare -p temp_ipport)")
    fastest_value=${temp_ipport[$fastest_srepo]}
	fastest_zart_value=${temp_zart_ipport[$fastest_srepo]}
    ;;
'ci_valid')
    pkgver=$mver
    echo "searhing valid_srepo ......"
    fastest_srepo=$(getsrepo "$(declare -p valid_ipport)")
    fastest_value=${valid_ipport[$fastest_srepo]}
	fastest_zart_value=${valid_zart_ipport[$fastest_srepo]}
    ;;
'gerrit_'*|*'valid_srepo'|'custom_'*)
    echo "searhing valid_srepo ......"
    fastest_srepo=$(getsrepo "$(declare -p valid_ipport)")
    fastest_value=${valid_ipport[$fastest_srepo]}
	fastest_zart_value=${valid_zart_ipport[$fastest_srepo]}
    ;;
'sh_valid_srepo'*)
    fastest_srepo="sh_valid_srepo"
    fastest_value="10.67.18.252:33377"
	fastest_zart_value="10.67.18.252:6000"
    ;;
'cd_valid_srepo'*)
    fastest_srepo="cd_valid_srepo"
    fastest_value="10.74.148.217:7777"
	fastest_zart_value="10.74.148.217:6000"
    ;;
'xa_valid_srepo'*)
    fastest_srepo="xa_valid_srepo"
    fastest_value="10.92.253.239:7777"
	fastest_zart_value="10.92.253.239:6000"
    ;;
'nj_valid_srepo'*)
    fastest_srepo="nj_valid_srepo"
    fastest_value="10.43.33.128:6666"
	fastest_zart_value="10.43.33.128:6000"
    ;;
*)
    echo "searhing release_srepo ......"
    fastest_srepo=$(getsrepo "$(declare -p release_ipport)")
    fastest_value=${release_ipport[$fastest_srepo]}
	fastest_zart_value=${release_zart_ipport[$fastest_srepo]}
    ;;
esac

if [ -z "$fastest_srepo" ]; then 
    exit 1
else
    echo -e "selecet srepo: \033[44;37m$fastest_srepo\033[0m \033[5m\033[34m$fastest_value\033[0m"
fi

ip=${fastest_value%:*}
port=${fastest_value#*:}
zart_ip=${fastest_zart_value%:*}
zart_port=${fastest_zart_value#*:}

./zartcli -S $zart_ip:$zart_port


rm -f pkg_ver.list
rm -f install.list
rm -f sync.list
case $1 in
*'temp_srepo'*)
    param=temp_srepo
    echo "pkg_ver.list ftp conf need defined by youself"
    ;;
*'ci_valid'*)
    param=ci_valid
    pkglist_ftp_path='/paasci/branch/'$branch'/pass_list'
    echo -e "pkg_ver.list ftp path set to \033[44;37mftp://zxpaas_rw:zxpaas_rw@10.67.18.8/$pkglist_ftp_path/\033[0m"
    wget -N -nd -q ftp://zxpaas_rw:zxpaas_rw@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
    ;;
*'valid_srepo'*)
    param=valid_srepo
    pkglist_ftp_path="/$(echo $pkgver|cut -c -5)/$(echo $pkgver|cut -c -8)/${pkgver}/intra-release"
    echo -e "pkg_ver.list ftp path set to \033[44;37mftp://zxpaas_rw:zxpaas_rw@10.67.18.8/$pkglist_ftp_path/\033[0m"
    wget -N -nd -q ftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
    ;;
'gerrit_'*)
    param=gerrit_review
    change_number=$(echo $1 | awk -F '_' '{print $2}')
    patch_set=$(echo $1 | awk -F '_' '{print $3}')
    pkglist_ftp_path="/paas/tmp/$pkgver/${change_number:0-2}/$change_number/$patch_set"
    echo -e "pkg_ver.list ftp path set to \033[44;37mftp://zxpaas_rw:zxpaas_rw@10.67.18.8/$pkglist_ftp_path/\033[0m"
    wget -N -nd -q ftp://zxpaas_rw:zxpaas_rw@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
    ;;
'custom_'*)
    param=custom
    change_number=$(echo $1 | awk -F '_' '{print $2}')
    patch_set=$(echo $1 | awk -F '_' '{print $3}')
    pkglist_ftp_path="/$(echo $pkgver|cut -c -5)/$(echo $pkgver|cut -c -8)/${pkgver}/customlist/$change_number/$patch_set"
    wget -N -nd -q ftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/* ./
    if [ ! -f pkg_ver.list ]; then
        pkglist_ftp_path="/$(echo $pkgver|cut -c -5)/$(echo $pkgver|cut -c -8)/${pkgver}/intra-release"
        wget -N -nd -q ftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
        if [ ! -f pkg_ver.list ]; then
            pkglist_ftp_path='/paasci/branch/'$branch'/pass_list'
            wget -N -nd -q ftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
        fi
    fi
    echo -e "pkg_ver.list ftp path set to \033[44;37mftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/\033[0m"
    ;;
*)
    param=release
    pkglist_ftp_path="/$(echo $pkgver|cut -c -5)/$(echo $pkgver|cut -c -8)/${pkgver}/intra-release"
    echo -e "pkg_ver.list ftp path set to \033[44;37mftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/\033[0m"
    wget -N -nd -q ftp://zxpaas:zxpaas@10.67.18.8/$pkglist_ftp_path/pkg_ver.list ./
    ;;
esac

    
case $1 in
*'temp_srepo')
    pdmcli_version=$(./zartcli -o query -m bin -i admin -n pdm-cli |grep -E '("reponame"|"name"|"version"|"created_at"|"status")'|tr -d '\n '|sed ':a;N;s/,\n/,/g;b a'|sed 's/\("reponame":[^,]*,\)/\n\1/g'|awk -F ',' '{for(i=5;i<=NF;i=i+3){ct=i-2;ver=i-1;st=i;print $ct","$2","$ver","$st}}'|grep complete|sort|tail -n 1|awk -F '"' '{print $12}')
    ;;
*)
    pdmcli_version=$(cat pkg_ver.list |grep pdm-cli|sed 's/.*"version"[^"]*"\([^"]*\)".*/\1/g')
    if [ -z "${pdmcli_version}" ]; then 
        echo "can't get pdmcli_version from pkg_ver.list"
        exit 1
    fi
    if [ $(./zartcli -o query -m bin -i admin -n pdm-cli -v ${pdmcli_version} | grep '"list": null'|wc -l) -gt 0 ]; then
        echo "pdm-cli $pdmcli_version not found in $fastest_srepo"
        exit 1
    fi
    ;;
esac

echo -e "pdm-cli version: \033[44;37m${pdmcli_version}\033[0m"

curl -X POST -s --connect-timeout 2 -d "version="$pkgver"&param="$param"&srepo="$fastest_srepo http://10.62.40.90/install-record.php 2>&1 > /dev/null &

./zartcli -o download -i admin -m bin -n pdm-cli -v ${pdmcli_version} -p ~/
rm zartcli zartcli.conf
cd ~/admin_pdm-cli_${pdmcli_version}/
tar zxvf pdm-cli.tar.gz > /dev/null
cd pdm-cli
./install.sh

sed "s/10.62.62.81/$ip/g" -i /etc/pdm/conf/softcenter.json
sed 's/"registry_port": "6666"/"registry_port": "'$port'"/g' -i /etc/pdm/conf/softcenter.json
sed 's/"swr_port": "6000"/"swr_port": "'$zart_port'"/g' -i /etc/pdm/conf/softcenter.json
sed "s/10.62.62.81:33666/${ip}:${port}/g" -i /etc/pdm/conf/paas.conf

case $1 in
'ci_valid'|'gerrit_'*)
    sed "s/^username=.*$/username=zxpaas_rw/g" -i /etc/pdm/conf/paas.conf
    sed "s/^password=.*$/password=zxpaas_rw/g" -i /etc/pdm/conf/paas.conf
    sed "s;^path=.*$;path=$pkglist_ftp_path;g" -i /etc/pdm/conf/paas.conf
    ;;
'custom_'*)
    cp /tmp/pkg_ver.list /etc/pdm/deploylist/
    cp /tmp/install.list /etc/pdm/deploylist/
    cp /tmp/sync.list /etc/pdm/deploylist/
    sed "s;^path=.*$;path=$pkglist_ftp_path;g" -i /etc/pdm/conf/paas.conf
    ;;
*)
    sed "s;^path=.*$;path=$pkglist_ftp_path;g" -i /etc/pdm/conf/paas.conf
    ;;
esac
