pid=$(ps -ef|grep -v grep|grep "smcenter-agent.jar"|awk '{print $2}')
if [ ! -z $pid ] ; then 
kill -9 $pid
fi
dd=`date +%Y%m%d%H%M%S`
file=smcenter_"$dd".log
cp smcenter.log  $file
nohup java -jar smcenter-agent.jar>smcenter.log 2>&1  &
tail -f smcenter.log
