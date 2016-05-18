This describes the docker local deployment:
	1/ Jenkins runs a mysql_test_container and a java_maven_container (jmc)
	2/ It copies the git project into tht jmc, whcih runs the tests and create the war
	3/ The war is copied into a tomcat_container, this container is pushed
	3/ The tomcat_container is pulled and commit with a prod_mysql_container

How to run it :
docker run  -p 8080:8080 --rm  -v /var/run/docker.sock:/var/run/docker.sock -v /home/yann/tools/jenkins:/var/jenkins_home --name jenkins_master jenkins_image


