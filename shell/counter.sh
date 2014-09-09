#!/bin/bash

logHome=/data/server/tomcats/tomcat7_8380_controlService/logs
resultLog=/data/shell/result.out
cd $logHome
filelist=`ls $logHome|grep FACEBOOK`
for file in $filelist
do
 if test -f $file
   then
      echo "$file :" >>$resultLog
      startTime="00:00:00"
      endTime="00:10:00"
     for time in $(seq 1 144)
      do
        taskCounter=`grep 'doSearchTask===>task:' $file|awk '{print $2}'|awk -F "," '{if($1>="'$startTime'" && $1<"'$endTime'") {print $1}}'|wc -l`
        errorCounter=`grep 'OAuthException,code:613' $file|awk '{print $2}'|awk -F "," '{if($1>="'$startTime'" && $1<"'$endTime'") {print $1}}'|wc -l`
        echo "[$startTime~$endTime]:  任务总数:$taskCounter  异常总数:$errorCounter" >> $resultLog
        startTime=$endTime
        temp=$(date +%s -d "$startTime")
        temp=$(($temp+10*60))
        endTime=`date +%T -d "1970-01-01 UTC $temp seconds"`
     done
 fi
done
