#!/usr/bin/env bash

/etc/init.d/yblog stop
rpm -Uvh $1 --force
#mysql -e "drop database yblog"
#mysql <  /usr/local/yblog/bin/init_db_cmd.sql
sed -i "s#uploadPath=.*#uploadPath=/var/www/webapp#g" /etc/yblog/settings.properties
sed -i "s#urlPath=.*#urlPath=https://www.d05660.top#g" /etc/yblog/settings.properties
/bin/cp /usr/local/yblog/bin/yblog /etc/init.d/yblog
/bin/cp /usr/local/yblog/conf/ssl.conf /etc/nginx/conf.d/ssl.conf
/etc/init.d/nginx reload
/etc/init.d/yblog start