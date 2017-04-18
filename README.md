# ssh
本项目主要用来搭建一个标准的java开发框架整合框架如下：

- freemarker
- spring mvc
- spring `4.3.1.RELEASE`
- spring data jpa `1.10.0.RELEASE`
- hiberante `5.1.0.Final`

springMvc+spring+spring jpa+hibernate ssh搭建

## 表结构
```
CREATE TABLE `ssh-users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sex` bit(1) NOT NULL,
  `age` tinyint(4) NOT NULL,
  `email` varchar(30) NOT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
