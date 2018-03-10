echo '==================='
echo 'Running : java ' $JVM_ARGS '-jar app.jar'
echo '==================='

publicPort=$(curl -m 2 169.254.170.20 | jq -re '.[].Ports[].PublicPort')
echo "The public port is ${publicPort}"
export PUBLIC_PORT=$publicPort

exec java $JVM_ARGS -DPUBLIC_PORT=$PUBLIC_PORT -jar app.jar
