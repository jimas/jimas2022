## protobuf 

### install

- [安装下载](https://github.com/google/protobuf/releases/download/v3.6.1/protobuf-all-3.6.1.tar.gz)
- tar -xzvf protobuf-all-3.6.1.tar.gz
- cd protobuf-all-3.6.1
- ./configure
    - 如果失败，提示gcc啥之类得，缺失相关依赖包（可通过yum grouplist,查看）安装组： yum groupinstall 'Development Tools'
    - 主要是产生Makefile
- make

- make install

- /usr/local/bin/protoc --version

### 使用
```shell
/usr/local/bin/protoc --java_out . phone.proto
```
