# demo
学习新知识的一些 demo
一些积累

## dubbo
 分布式框架学习摸索
## file
 <li><a href=https://github.com/emperor-liu/demo/blob/master/file/src/main/java/com/lljqiu/demo/file/utils/ItextPdfTest.java>[`ItextPdfTest` PDF 添加水印操作]</a></li>
 <li><a href=https://github.com/emperor-liu/demo/blob/master/file/src/main/java/com/lljqiu/demo/file/utils/ZipUtils.java>[`ZipUtils` 文件压缩]</a></li>

## formValidator
  表单校验
  <a href="https://github.com/emperor-liu/demo/blob/master/formValidator.html">demoPage</a><br>
  <a href="https://github.com/yairEO/validator">fork</a> 看着不错自己再整理一遍
  ```
  /**
    required  - form 提交前必须校验
    validate-repeat="password"  用于判断两个输入一致性
    min 最小值
    max 最大值
    minlength 最小长度
    maxlength 最大长度
    pattern  自定义的正则校验
    let settings={messages:{required:'test'},rules:{number:/^\d+$/}}
    new FormUtils(settings);  可以传递参数
    */
    function testSub(){
            var $form = $("#addOrUpdateForm");
            var validatorUtils = new FormUtils();
            var result = validatorUtils.validator($form);
            console.info(result);
            if(result){
                console.info("submit");
            }
        }
  ```
