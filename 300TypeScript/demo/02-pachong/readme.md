初始化项目
npm init -y


初始化ts的配置
tsc --int


全局卸载 ts-node 、 TS
cnpm uninstall ts-node -g
cnpm uninstall typescript -g

DEV安装ts-node ts
cnpm install -D ts-node
cnpm install -D typescript


按照superagent，用于发起网络请求
npm install superagent --save
// ts -> .d.ts 翻译文件 -> js   ts怎么引入js @type/xxx
npm install @types/superagent -D


运行测试
npm run dev

查看npm配置信息
npm config list

修改npm的镜像地址，提高npm的执行速度
npm config set registry https://registry.npm.taobao.org


用于解析html未jquery相关API
npm install cheerio --save
npm install @types/cheerio -D