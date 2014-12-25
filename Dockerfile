FROM centos:centos6

MAINTAINER Takeshi Nakano <tf0054@gmail.com>

RUN yum -y update
RUN yum -y install git java-1.7.0-openjdk
RUN git clone https://github.com/tf0054/simplestweb.git
ADD https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein /usr/bin/lein
ADD https://get.docker.com/builds/Linux/x86_64/docker-1.3.3 /usr/bin/docker
RUN chmod a+x /usr/bin/lein /usr/bin/docker

WORKDIR /simplestweb
RUN /usr/bin/lein deps

EXPOSE 80

ENTRYPOINT ["/usr/bin/lein","trampoline","run"]
