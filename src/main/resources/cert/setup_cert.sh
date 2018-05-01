#!/usr/bin/env bash

if [ ! -f "/usr/local/letsEncrypt/conf/account.key" ]; then
    openssl genrsa 4096 > /usr/local/letsEncrypt/conf/account.key
fi
if [ ! -d "/etc/nginx/cert" ]; then
    mkdir -p "/etc/nginx/cert"
fi
if [ ! -f "/etc/nginx/cert/d05660.key" ]; then
    openssl genrsa 4096 > /etc/nginx/cert/d05660.key
fi
if [ ! -f "/usr/local/letsEncrypt/conf/wwwd05660.csr" ]; then
    openssl req -new -sha256 -key /etc/nginx/cert/conf/d05660.key -subj "/CN=www.d05660.top" > /usr/local/letsEncrypt/conf/wwwd05660.csr
fi
if [ ! -f "/usr/local/letsEncrypt/conf/d05660.csr" ]; then
    openssl req -new -sha256 -key /etc/nginx/cert/conf/d05660.key -subj "/CN=d05660.top" > /usr/local/letsEncrypt/conf/d05660.csr
fi
wget https://raw.githubusercontent.com/diafygi/acme-tiny/master/acme_tiny.py -O /usr/local/letsEncrypt/bin/acme_tiny.py
if [ ! -d "/var/www/challenges" ]; then
    mkdir -p "/var/www/challenges"
fi

sed -i "/renew_cert/d" /etc/crontab
echo '0 0 1 * * root sh /usr/local/letsEncrypt/bin/renew_cert.sh > /dev/null 2>&1' >>/etc/crontab

exit 0