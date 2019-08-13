export JAVA_HOME='/usr/local/jdk1.8.0_131/'
export PATH=$JAVA_HOME/bin:$PATH
JAVA="java -jar -server -Xms212m -Xmx424m -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+UseConcMarkSweepGC "
#jar\u5305\u540d\u79f0  \u522b\u540d
PROGRAM="zebra-yritsz-api-provider-2.0.jar zebra provider"
if [ $1 == "start" ] 
  then   
   echo $PROGRAM  "starting !"
  nohup $JAVA $PROGRAM >/dev/null 2>&1 &
  echo "For details, please check the project log!"
elif [ $1 == "stop" ]
  then
  echo $PROGRAM  "stopping !"
  #\u67e5\u770b\u662f\u5426\u5b58\u5728\u8fd9\u4e2a\u8fdb\u7a0b\uff0c\u8fd4\u56de\u7ed3\u679c\u662f\u6570\u5b57\uff0c
  pcount=`ps -ef | grep "$PROGRAM" | grep -v "grep" | wc -l`
  if [ $pcount -gt 0 ]; then  #\u8fd4\u56de\u7684\u6570\u5b57\u4e0d\u5c0f\u4e8e0 \u8bf4\u660e\u5b58\u5728\u8fdb\u7a0b
        #\u83b7\u53d6\u8fdb\u7a0bID \u5e76 \u6740\u6389\u8fdb\u7a0b
	pid=`ps -ef | grep "$PROGRAM" | grep -v "grep" | awk '{ print $2; }'`
	kill $pid
  else
	echo "$PROGRAM" not running;
  fi
elif [ $1 == "restart" ]
  then
  echo $PROGRAM  "restart !"
  pcount=`ps -ef | grep "$PROGRAM" | grep -v "grep" | wc -l`
  if [ $pcount -gt 0 ]; then  #\u8fd4\u56de\u7684\u6570\u5b57\u4e0d\u5c0f\u4e8e0 \u8bf4\u660e\u5b58\u5728\u8fdb\u7a0b
        #\u83b7\u53d6\u8fdb\u7a0bID \u5e76 \u6740\u6389\u8fdb\u7a0b
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
