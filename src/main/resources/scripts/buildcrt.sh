#!/bin/bash
mkdir -p /usr/local/letsEncrypt
openssl genrsa 4096 > /usr/local/letsEncrypt/account.key
mkdir -p /etc/nginx/cert
openssl genrsa 4096 > /etc/nginx/cert/d05660.key
openssl req -new -sha256 -key /etc/nginx/cert/d05660.key -subj "/CN=www.d05660.top" > /usr/local/letsEncrypt/wwwd05660.csr
openssl req -new -sha256 -key /etc/nginx/cert/d05660.key -subj "/CN=d05660.top" > /usr/local/letsEncrypt/d05660.csr
wget https://raw.githubusercontent.com/diafygi/acme-tiny/master/acme_tiny.py -O /usr/local/letsEncrypt/acme_tiny.py
mkdir -p /var/www/challenges
cat << 'EOF' > /usr/local/letsEncrypt/nossl.conf
server {
    listen 80;
    server_name _;
    location ^~ /.well-known/acme-challenge/ {
        alias /var/www/challenges/;
        try_files $uri =404;
    }
}
EOF

cat << EEF > /usr/local/letsEncrypt/renew_cert.sh
#!/bin/bash
/bin/mv /etc/nginx/conf.d/ssl.conf /etc/nginx/conf.d/ssl.conf.bak
/bin/cp /usr/local/letsEncrypt/nossl.conf /etc/nginx/conf.d/ssl.conf
service nginx reload
python /usr/local/letsEncrypt/acme_tiny.py --account-key /usr/local/letsEncrypt/account.key \
    --csr /usr/local/letsEncrypt/wwwd05660.csr --acme-dir /var/www/challenges/ > /tmp/wwwsigned.crt || exit
python /usr/local/letsEncrypt/acme_tiny.py --account-key /usr/local/letsEncrypt/account.key \
    --csr /usr/local/letsEncrypt/d05660.csr --acme-dir /var/www/challenges/ > /tmp/signed.crt || exit
wget -O - https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem > /tmp/intermediate.pem
cat /tmp/wwwsigned.crt /tmp/intermediate.pem >/etc/nginx/cert/wwwd05660.pem
cat /tmp/signed.crt /tmp/intermediate.pem >/etc/nginx/cert/d05660.pem
/bin/mv /etc/nginx/conf.d/ssl.conf.bak /etc/nginx/conf.d/ssl.conf
service nginx reload
EEF

sed -i "/renew_cert/d" /etc/crontab
echo '0 0 1 * * root sh /usr/local/letsEncrypt/renew_cert.sh > /dev/null 2>&1' >>/etc/crontab

exit 0
