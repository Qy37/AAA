# 方案1：关闭 SSL 验证（不推荐用于生产环境）
git config --global http.sslVerify false

# 方案2：使用 HTTPS 替代
git remote add origin https://github.com/Qy37/gbl.git

# 方案3：更新 Git 的 SSL 证书
git config --system http.sslcainfo "C:/Program Files/Git/mingw64/ssl/certs/ca-bundle.crt"

# 设置完成后推送代码
git add .
git commit -m "Initial commit"
git branch -M main
git push -u origin main
