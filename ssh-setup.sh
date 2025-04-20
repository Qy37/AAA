# 1. 生成 SSH 密钥
ssh-keygen -t rsa -b 4096 -C "你的邮箱"

# 2. 复制公钥内容（需要添加到 GitHub）
cat ~/.ssh/id_rsa.pub

# 3. 使用 SSH 地址克隆
git clone git@github.com:Qy37/AAA.git c:/Desktop/A/AAA
