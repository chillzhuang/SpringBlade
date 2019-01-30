
#使用说明，用来提示输入参数
usage() {
	echo "Usage: sh 执行脚本.sh [port|base|modules|stop|rm|rmiNoneTag|services|health]"
	exit 1
}

#开启所需端口
port(){
	firewall-cmd --add-port=88/tcp --permanent
	firewall-cmd --add-port=8500/tcp --permanent
	firewall-cmd --add-port=3306/tcp --permanent
	firewall-cmd --add-port=3379/tcp --permanent
	firewall-cmd --add-port=7002/tcp --permanent
	service firewalld restart
}

#启动基础模块
base(){
	if test ! -f "/docker/nginx/nginx.conf" ;then
		mkdir /docker/nginx
		cp nginx.conf /docker/nginx/nginx.conf
	fi
	docker-compose up -d consul-server1 consul-server2 consul-server3 consul-node1 consul-node2 consul-nginx blade-redis blade-gateway1 blade-gateway2 blade-gateway3 blade-config-server blade-admin
}

#启动程序模块
modules(){
	docker-compose up -d blade-auth blade-user blade-desk blade-system blade-log
}

#获取注册服务
services(){
	RESULT=$(curl -s 127.0.0.1:8500/v1/catalog/services)
	echo $RESULT
}

#获取检查健康
health(){
	RESULT=$(curl -s 127.0.0.1:8500/v1/health/checks/blade-config-server)
	echo $RESULT
}

#关闭所有模块
stop(){
	docker-compose stop
}

#删除所有模块
rm(){
	docker-compose rm
}

#删除Tag为空的镜像
rmiNoneTag(){
	docker images|grep none|awk '{print $3}'|xargs docker rmi -f
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"port")
	port
;;
"base")
	base
;;
"modules")
	modules
;;
"stop")
	stop
;;
"rm")
	rm
;;
"rmiNoneTag")
	rmiNoneTag
;;
"services")
	services
;;
"health")
	health
;;
*)
	usage
;;
esac
