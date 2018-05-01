#!/usr/bin/env bash

systemctl stop meblog
rpm -Uvh $1 --force
#mysql -e "drop database meblog"
#mysql <  /usr/local/meblog/bin/init_db_cmd.sql
/bin/cp /usr/local/meblog/bin/meblog.service /usr/lib/systemd/system/meblog.service
/bin/cp /usr/local/meblog/conf/ssl.conf /etc/nginx/conf.d/ssl.conf
systemctl daemon-reload
sed -i "s#uploadPath=.*#uploadPath=/var/www/webroot#g" /etc/meblog/settings.properties
sed -i "s#urlPath=.*#urlPath=https://www.d05660.top#g" /etc/meblog/settings.properties
systemctl reload nginx
systemctl start meblog