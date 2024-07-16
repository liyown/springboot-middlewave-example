.class public Lsql/ObjectParamsTest;
.super Ljava/lang/Object;
.source "ObjectParamsTest.java"


# annotations
.annotation runtime Lorg/springframework/boot/test/context/SpringBootTest;
    classes = {
        Lcom/lyw/HMDPMain;
    }
.end annotation


# instance fields
.field private userMapper:Lcom/lyw/mapper/UserMapper;
    .annotation runtime Ljakarta/annotation/Resource;
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 21
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public test2()V
    .registers 4
    .annotation runtime Lorg/junit/jupiter/api/Test;
    .end annotation

    .prologue
    .line 40
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 42
    .local v1, "list":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/Integer;>;"
    const/16 v2, 0xa

    new-array v0, v2, [Ljava/lang/Integer;

    .line 45
    .local v0, "a":[Ljava/lang/Object;
    return-void
.end method

.method public testObjectParams()V
    .registers 7
    .annotation runtime Lorg/junit/jupiter/api/Test;
    .end annotation

    .prologue
    const-wide/16 v4, 0x1

    .line 28
    new-instance v1, Lcom/lyw/pojo/User;

    invoke-direct {v1}, Lcom/lyw/pojo/User;-><init>()V

    .line 29
    .local v1, "user":Lcom/lyw/pojo/User;
    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/lyw/pojo/User;->setId(Ljava/lang/Long;)V

    .line 31
    new-instance v0, Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper;

    invoke-direct {v0}, Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper;-><init>()V

    .line 32
    .local v0, "objectUpdateWrapper":Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper;, "Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper<Lcom/lyw/pojo/User;>;"
    const-string v2, "id"

    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    invoke-virtual {v0, v2, v3}, Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper;->eq(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 33
    const-string v2, "asd"

    new-instance v3, Lcom/lyw/pojo/User;

    invoke-direct {v3}, Lcom/lyw/pojo/User;-><init>()V

    invoke-virtual {v0, v2, v3}, Lcom/baomidou/mybatisplus/core/conditions/update/UpdateWrapper;->set(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 35
    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;

    iget-object v3, p0, Lsql/ObjectParamsTest;->userMapper:Lcom/lyw/mapper/UserMapper;

    invoke-static {v4, v5}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v4

    invoke-interface {v3, v4}, Lcom/lyw/mapper/UserMapper;->selectByIds(Ljava/lang/Long;)Lcom/lyw/pojo/User;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/Object;)V

    .line 37
    return-void
.end method
