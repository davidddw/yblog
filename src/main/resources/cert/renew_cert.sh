#!/usr/bin/env bash

/bin/mv /usr/local/letsEncrypt/conf/nossl.conf /etc/nginx/conf.d/ssl.conf
service nginx reload
python /usr/local/letsEncrypt/bin/acme_tiny.py --account-key /usr/local/letsEncrypt/conf/account.key \
    --csr /usr/local/letsEncrypt/conf/wwwd05660.csr --acme-dir /var/www/challenges/ > /tmp/wwwsigned.crt || exit
python /usr/local/letsEncrypt/bin/acme_tiny.py --account-key /usr/local/letsEncrypt/conf/account.key \
    --csr /usr/local/letsEncrypt/conf/d05660.csr --acme-dir /var/www/challenges/ > /tmp/signed.crt || exit
wget -O - https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem > /tmp/intermediate.pem
cat /tmp/wwwsigned.crt /tmp/intermediate.pem >/etc/nginx/cert/wwwd05660.pem
cat /tmp/signed.crt /tmp/intermediate.pem >/etc/nginx/cert/d05660.pem
/bin/mv /usr/local/letsEncrypt/conf/ssl.conf /etc/nginx/conf.d/ssl.conf
service nginx reload