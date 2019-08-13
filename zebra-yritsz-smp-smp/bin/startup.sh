export JAVA_HOME='/usr/local/jdk1.8.0_131/'
export PATH=$JAVA_HOME/bin:$PATH
JAVA="java -jar -server -Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+UseConcMarkSweepGC "
#jar包名称  别名
PROGRAM="zebra-yritsz-smp-2.0.jar zebra SMP"
if [ $1 == "start" ] 
  then   
   echo $PROGRAM  "starting !"
  nohup $JAVA $PROGRAM >/dev/null 2>&1 &
  echo "For details, please check the project log!"
elif [ $1 == "stop" ]
  then
  echo $PROGRAM  "stopping !"
  #查看是否存在这个进程，返回结果是数字，
  pcount=`ps -ef | grep "$PROGRAM" | grep -v "grep" | wc -l`
  if [ $pcount -gt 0 ]; then  #返回的数字不小于0 说明存在进程
        #获取进程ID 并 杀掉进程
	pid=`ps -ef | grep "$PROGRAM" | grep -v "grep" | awk '{ print $2; }'`
	kill $pid
  else
	echo "$PROGRAM" not running;
  fi
elif [ $1 == "restart" ]
  then
  echo $PROGRAM  "restart !"
  pcount=`ps -ef | grep "$PROGRAM" | grep -v "grep" | wc -l`
  if [ $pcount -gt 0 ]; then  #返回的数字不小于0 说明存在进程
        #获取进程ID 并 杀掉进程
	pid=`ps -ef | grep "$PROGRAM" | grep -v "grep" | awk '{ print $2; }'`
	kill $pid
	sleep 1
  fi
   nohup $JAVA $PROGRAM >/dev/null 2>&1 & 
  echo "For details, please check the project log!"
else
  echo "Please make sure the positon variable is [start] [stop] [restart]."
fi
exit 0
