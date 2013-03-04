/**
 * 歌曲列表相关js。
 * 歌曲列表通常由ajax动态载入，某些插件、元素需要重新绑定事件生效,这些公共逻辑放在这里。
 *
 * 可用$().live方法解决的事件监听如歌曲详情的展开等，均在global js中。
 */

$('*[data-toggle="tooltip"]').tooltip();
