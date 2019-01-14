# finch-sample

This is a sample project for developers interested in Scala and [Finch](https://github.com/finagle/finch) framework.

This project is featuring some basic and general topics about REST API.

## Preface

This project is intended to be used on Mac OS X or Linux.

And following instructions are only for Mac OS X, sorry.

# Usage

*As with this projects, you must have JDK 1.8 and sbt installed.*

First of all, you need to install MySQL.

```bash
brew install mysql
mysql.server start
```

And you need to create db user `sampleuser` with password `changeme` like below.

```bash
CREATE USER 'sampleuser'@'localhost' IDENTIFIED by 'changeme';
GRANT ALL PRIVILEGES ON *.* TO 'sampleuser'@'localhost';
```

And you also need to create db `finchdb` and table `users` like below.

```bash
CREATE DATABASE finchdb;

use finchdb;

CREATE TABLE `users` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `email`       VARCHAR(255) NOT NULL,
  `screen_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
```

After that, you can start this app like below:

```bash
# Run within finch directory.
sbt run
```

Now you can send APIs like below.

```bash
# Get user list.
curl http://localhost:8080/users

# Get a user info.
curl http://localhost:8080/users/`id`

# Post a user.
curl -X POST -d "email=finch-man@example.com" -d "screen_name=finch-man" http://localhost:8080/users
```

# References

* https://blog.dakatsuka.jp/2015/12/07/finch.html (Japanese)