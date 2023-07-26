#!/bin/sh
echo "======== 开始安装万界星空免费MES ========"

echo "======== 开始安装Docker ========"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
apt-get install -y software-properties-common
add-apt-repository    "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
apt-get install docker-ce  -y
echo "======== Docker安装完成 ========"

echo "======== 修改Docker镜像 开始 ========"
sudo mkdir -p /home/docker
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://oav9ge9b.mirror.aliyuncs.com"],
"log-driver": "json-file",
"log-opts": {
"max-size": "300m",
"max-file": "5"
},
"data-root": "/home/docker"

}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
echo "======== 修改Docker镜像 完成 ========"


sudo curl -L https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose

docker-compose up -d

echo "======== 万界星空免费MES安装完成 ========"
