# 基于git开发流程



## 概括

- 使用git作为版本控制
- 代码托管github
- 使用issues实现功能描述和问题讨论
- 使用pull request进行合并代码
- 使用git flow进行工作流程控制 **NEW**



## Git使用规范

### 分支

- **master** 只能用来包括产品代码。你不能直接工作在这个 master 分支上，而是在其他指定的，独立的特性分支中（这方面我们会马上谈到）。不直接提交改动到 *master* 分支上也是很多工作流程的一个共同的规则。
- **develop** 是你进行任何新的开发的基础分支。当你开始一个新的功能分支时，它将是_开发_的基础。另外，该分支也汇集所有已经完成的功能，并等待被整合到 *master* 分支中。



## Git-flow使用

### 开发一个新功能

```shell
$ git flow feature start 功能名	
$ # 例如:  git flow feature start add-gitflow-doc
```

输出

```
Switched to a new branch 'feature/add-gitflow-doc'

Summary of actions:
- A new branch 'feature/add-gitflow-doc' was created, based on 'develop'
- You are now on branch 'feature/add-gitflow-doc'

Now, start committing on your feature. When done, use:

     git flow feature finish add-gitflow-doc

```



### 