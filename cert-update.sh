# 1. 下载最新的 cacert.pem
curl -o "C:/cacert.pem" "https://curl.se/ca/cacert.pem"

# 2. 配置 Git 使用新证书
git config --global http.sslCAinfo "C:/cacert.pem"

# 3. 重新克隆
git clone https://github.com/Qy37/AAA.git c:/Desktop/A/AAA
