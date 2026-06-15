1. prompts para gerar o aspeto que ja nao conseguimos obter eles
2. pede para dar merge entre branches da main com a do remote com a master do remote
3. volta a traz no merge que foi feito? ele apagou grande parte dos ficheiros
4. como se teve que criar a view de @src/main/java/views/VeiculosDocumentosView.java entao em @src/main/java/views/VeiculosView.java e no seu viewmodel ja nao precisam ter coisas relaciondas aos documentos pois neste caso dos veiculos vao estar separados por ficheiros depois os outros vesse entao cria um novo viewModel move coisas que estejam no @src/main/java/viewModels/VeiculosViewModel.java para esse novo viewModel e em @src/main/java/views/VeiculosDocumentosView.java chama esse viewmodel
5. -*- mode: compilation; default-directory: "/var/home/fabio/Documents/projeto_final/" -*-
Compilation started at Thu May 28 22:58:55

correr java
Projeto Gradle detetado em /var/home/fabio/Documents/projeto_final
> Task :spotlessJava UP-TO-DATE
> Task :spotlessJavaApply UP-TO-DATE
> Task :spotlessApply UP-TO-DATE
> Task :compileJava
> Task :processResources UP-TO-DATE
> Task :classes

> Task :run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: Erro ao executar SELECT: SELECT id, veiculo_id, titulo, tipo, data_validade, seguradora, cobertura, valor, notas FROM documentos_veiculo | Error parsing time stamp
	at fabiorodrigues.bricks.data.Query.execute(Query.java:477)
	at viewModels.VeiculosDocumentosViewModel.verDocumentos(VeiculosDocumentosViewModel.java:37)
	at viewModels.VeiculosDocumentosViewModel.carregarDocumentos(VeiculosDocumentosViewModel.java:17)
	at views.VeiculosDocumentosView.<init>(VeiculosDocumentosView.java:14)
	at components.VeiculosCard.lambda$render$0(VeiculosCard.java:77)
	at fabiorodrigues.bricks.components.Button.lambda$render$1(Button.java:142)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
	at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics@21.0.5/javafx.scene.Node.fireEvent(Node.java:8875)
	at javafx.controls@21.0.5/javafx.scene.control.Button.fire(Button.java:203)
	at javafx.controls@21.0.5/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:207)
	at javafx.controls@21.0.5/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
	at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics@21.0.5/javafx.scene.Scene$MouseHandler.process(Scene.java:3984)
	at javafx.graphics@21.0.5/javafx.scene.Scene.processMouseEvent(Scene.java:1890)
	at javafx.graphics@21.0.5/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2708)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
	at javafx.graphics@21.0.5/com.sun.glass.ui.View.handleMouseEvent(View.java:551)
	at javafx.graphics@21.0.5/com.sun.glass.ui.View.notifyMouse(View.java:937)
	at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)
	at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication.lambda$runLoop$10(GtkApplication.java:263)
	at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.sql.SQLException: Error parsing time stamp
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:280)
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:295)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.getValue(ResultMapper.java:306)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.map(ResultMapper.java:162)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.mapList(ResultMapper.java:39)
	at fabiorodrigues.bricks.data.Query.execute(Query.java:474)
	... 70 more
Caused by: java.text.ParseException: Unparseable date: "2026-05-19" does not match (\p{Nd}++)\Q-\E(\p{Nd}++)\Q-\E(\p{Nd}++)\Q \E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q.\E(\p{Nd}++)
	at org.sqlite.date.FastDateParser.parse(FastDateParser.java:311)
	at org.sqlite.date.FastDateFormat.parse(FastDateFormat.java:449)
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:278)
	... 75 more
Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: Erro ao executar SELECT: SELECT id, veiculo_id, titulo, tipo, data_validade, seguradora, cobertura, valor, notas FROM documentos_veiculo | Error parsing time stamp
	at fabiorodrigues.bricks.data.Query.execute(Query.java:477)
	at viewModels.VeiculosDocumentosViewModel.verDocumentos(VeiculosDocumentosViewModel.java:37)
	at viewModels.VeiculosDocumentosViewModel.carregarDocumentos(VeiculosDocumentosViewModel.java:17)
	at views.VeiculosDocumentosView.<init>(VeiculosDocumentosView.java:14)
	at components.VeiculosCard.lambda$render$0(VeiculosCard.java:77)
	at fabiorodrigues.bricks.components.Button.lambda$render$1(Button.java:142)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
	at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics@21.0.5/javafx.scene.Node.fireEvent(Node.java:8875)
	at javafx.controls@21.0.5/javafx.scene.control.Button.fire(Button.java:203)
	at javafx.controls@21.0.5/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:207)
	at javafx.controls@21.0.5/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
	at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
	at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
	at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics@21.0.5/javafx.scene.Scene$MouseHandler.process(Scene.java:3984)
	at javafx.graphics@21.0.5/javafx.scene.Scene.processMouseEvent(Scene.java:1890)
	at javafx.graphics@21.0.5/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2708)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
	at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
	at javafx.graphics@21.0.5/com.sun.glass.ui.View.handleMouseEvent(View.java:551)
	at javafx.graphics@21.0.5/com.sun.glass.ui.View.notifyMouse(View.java:937)
	at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)
	at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication.lambda$runLoop$10(GtkApplication.java:263)
	at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.sql.SQLException: Error parsing time stamp
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:280)
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:295)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.getValue(ResultMapper.java:306)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.map(ResultMapper.java:162)
	at fabiorodrigues.bricks.data.mapper.ResultMapper.mapList(ResultMapper.java:39)
	at fabiorodrigues.bricks.data.Query.execute(Query.java:474)
	... 70 more
Caused by: java.text.ParseException: Unparseable date: "2026-05-19" does not match (\p{Nd}++)\Q-\E(\p{Nd}++)\Q-\E(\p{Nd}++)\Q \E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q.\E(\p{Nd}++)
	at org.sqlite.date.FastDateParser.parse(FastDateParser.java:311)
	at org.sqlite.date.FastDateFormat.parse(FastDateFormat.java:449)
	at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:278)
	... 75 more

[Incubating] Problems report is available at: file:///var/home/fabio/Documents/projeto_final/build/reports/problems/problems-report.html

Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/9.4.1/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 42s
5 actionable tasks: 2 executed, 3 up-to-date

Compilation finished at Thu May 28 22:59:37, duration 42.2 s


qual e o problema?
6. o .settings/org.eclipse.buildship.core.prefs como temos 3 sistemas diferentes aqui a utillizarem o projeto eu com portatil a ser macos este a ser linux, e meu colega de grupo com windows esse ficheiro esta sempre a ser alterado mesmo estando no gitignore ele continua a ser identificado como alteracoes por ter sido enviado antes de ter sido adicionado a ele entao remove eles do remote, nas duas branchs para que o gitignore passe a funcionar
7. e o que e este pasta que esta tambem sempre aparecer -666321368
8. fabio@fabio-torre ~/D/projeto_final (master)> correr java
Projeto Gradle detetado em /home/fabio/Documents/projeto_final

> Task :run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: Erro ao executar SELECT: SELECT id, veiculo_id, titulo, tipo, data_validade, seguradora, cobertura, valor, notas FROM documentos_veiculo | Error parsing time stamp
        at fabiorodrigues.bricks.data.Query.execute(Query.java:477)
        at viewModels.VeiculosDocumentosViewModel.verDocumentos(VeiculosDocumentosViewModel.java:51)
        at viewModels.VeiculosDocumentosViewModel.carregarDocumentos(VeiculosDocumentosViewModel.java:31)
        at views.VeiculosDocumentosView.<init>(VeiculosDocumentosView.java:36)
        at components.VeiculosCard.lambda$render$0(VeiculosCard.java:92)
        at fabiorodrigues.bricks.components.Button.lambda$render$1(Button.java:142)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
        at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics@21.0.5/javafx.scene.Node.fireEvent(Node.java:8875)
        at javafx.controls@21.0.5/javafx.scene.control.Button.fire(Button.java:203)
        at javafx.controls@21.0.5/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:207)
        at javafx.controls@21.0.5/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
        at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics@21.0.5/javafx.scene.Scene$MouseHandler.process(Scene.java:3984)
        at javafx.graphics@21.0.5/javafx.scene.Scene.processMouseEvent(Scene.java:1890)
        at javafx.graphics@21.0.5/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2708)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
        at javafx.graphics@21.0.5/com.sun.glass.ui.View.handleMouseEvent(View.java:551)
        at javafx.graphics@21.0.5/com.sun.glass.ui.View.notifyMouse(View.java:937)
        at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)
        at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication.lambda$runLoop$10(GtkApplication.java:263)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.sql.SQLException: Error parsing time stamp
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:280)
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:295)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.getValue(ResultMapper.java:306)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.map(ResultMapper.java:162)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.mapList(ResultMapper.java:39)
        at fabiorodrigues.bricks.data.Query.execute(Query.java:474)
        ... 58 more
Caused by: java.text.ParseException: Unparseable date: "2026-07-24" does not match (\p{Nd}++)\Q-\E(\p{Nd}++)\Q-\E(\p{Nd}++)\Q \E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q.\E(\p{Nd}++)
        at org.sqlite.date.FastDateParser.parse(FastDateParser.java:311)
        at org.sqlite.date.FastDateFormat.parse(FastDateFormat.java:449)
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:278)
        ... 63 more
Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: Erro ao executar SELECT: SELECT id, veiculo_id, titulo, tipo, data_validade, seguradora, cobertura, valor, notas FROM documentos_veiculo | Error parsing time stamp
        at fabiorodrigues.bricks.data.Query.execute(Query.java:477)
        at viewModels.VeiculosDocumentosViewModel.verDocumentos(VeiculosDocumentosViewModel.java:51)
        at viewModels.VeiculosDocumentosViewModel.carregarDocumentos(VeiculosDocumentosViewModel.java:31)
        at views.VeiculosDocumentosView.<init>(VeiculosDocumentosView.java:36)
        at components.VeiculosCard.lambda$render$0(VeiculosCard.java:92)
        at fabiorodrigues.bricks.components.Button.lambda$render$1(Button.java:142)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
        at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics@21.0.5/javafx.scene.Node.fireEvent(Node.java:8875)
        at javafx.controls@21.0.5/javafx.scene.control.Button.fire(Button.java:203)
        at javafx.controls@21.0.5/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:207)
        at javafx.controls@21.0.5/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:232)
        at javafx.base@21.0.5/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:189)
        at javafx.base@21.0.5/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
        at javafx.base@21.0.5/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
        at javafx.base@21.0.5/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
        at javafx.base@21.0.5/javafx.event.Event.fireEvent(Event.java:198)
        at javafx.graphics@21.0.5/javafx.scene.Scene$MouseHandler.process(Scene.java:3984)
        at javafx.graphics@21.0.5/javafx.scene.Scene.processMouseEvent(Scene.java:1890)
        at javafx.graphics@21.0.5/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2708)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:411)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:301)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:450)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:424)
        at javafx.graphics@21.0.5/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:449)
        at javafx.graphics@21.0.5/com.sun.glass.ui.View.handleMouseEvent(View.java:551)
        at javafx.graphics@21.0.5/com.sun.glass.ui.View.notifyMouse(View.java:937)
        at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication._runLoop(Native Method)        at javafx.graphics@21.0.5/com.sun.glass.ui.gtk.GtkApplication.lambda$runLoop$10(GtkApplication.java:263)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.sql.SQLException: Error parsing time stamp
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:280)
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:295)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.getValue(ResultMapper.java:306)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.map(ResultMapper.java:162)
        at fabiorodrigues.bricks.data.mapper.ResultMapper.mapList(ResultMapper.java:39)
        at fabiorodrigues.bricks.data.Query.execute(Query.java:474)
        ... 58 more
Caused by: java.text.ParseException: Unparseable date: "2026-07-24" does not match (\p{Nd}++)\Q-\E(\p{Nd}++)\Q-\E(\p{Nd}++)\Q \E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q.\E(\p{Nd}++)
        at org.sqlite.date.FastDateParser.parse(FastDateParser.java:311)
        at org.sqlite.date.FastDateFormat.parse(FastDateFormat.java:449)
        at org.sqlite.jdbc3.JDBC3ResultSet.getDate(JDBC3ResultSet.java:278)
        ... 63 more
││████████████▊··│ 85% EXECUTING [3m 21s]
> :run
^C^C⏎                                                                                                                                                                         fabio@fabio-torre ~/D/projeto_final (master) [SIGINT]>

o problema foi na inserçao @src/main/java/viewModels/VeiculosDocumentosViewModel.java foi sem o tempo mesmo que seja zeros, agora nao consegue dar parse ajusta na base de dados e corrige a insersao
9. hint: You have divergent branches and need to specify how to reconcile them.
hint: You can do so by running one of the following commands sometime before
hint: your next pull:
hint:
hint:   git config pull.rebase false  # merge
hint:   git config pull.rebase true   # rebase
hint:   git config pull.ff only       # fast-forward only
hint:
hint: You can replace "git config" with "git config --global" to set a default
hint: preference for all repositories. You can also pass --rebase, --no-rebase,
hint: or --ff-only on the command line to override the configured default per
hint: invocation.
fatal: Need to specify how to reconcile divergent branches.

inicia para fazer o merge e verifico manualmente eles a indicar o que fica onde
10. ao escolher escolhi mal em alguns volta ao inicio do merge
11. ve @src/main/java/viewModels/DocumentosViewModel.java e ajusta o @src/main/java/viewModels/SubscricaoViewModel.java com mesma estrutura e ter para ter a lista de Subscricoes e de documentos, no caso das subscricoes cada subscricao devera so ter 1 subscricao
12. extrai o que esta dentro do  ItemsColumn e cria o SubscricaoCard como tenho nos outros e vai para a pasta @src/main/java/components o como esta ainda e como rasconho os dias em atraso tem que ter mesma logica que outros cards tem em relacao as cores nome da subscricao e valor da subscricao e que ficam a bold o tipo e o plano ficam na mesma linha por debaixo do nome ficam cizentos e ligeiramnete mais pequenos
13. em todas as views tem o modal que esta a ser criado juntamente com o titulo e o modal que se cria a parte para a edicao dos dados, cria um componete e mete em @src/main/java/components onde estar unificado o modal, em termos de estrutura do codigo ja sei que ao criares nao vai estar a 100% como quero entao faz e depois ajusta-se
14. no projeto foi adicionado o sistema de notificacoes no sistema mas no caso do mac penso que nao estavam a funcionar podes confirmar isso
15. foi criado uma novo item na sidebar Settings mas ainda nao foi criado a view em views ve os outros e cria a base seguindo mesma estrutura
16. no Calendario tem o BadgeEstado com record extrai ele e mete nos components sendo que ele possa ser usado em varios sitios
17. passa pelo codigo e cria o javadocs nos metodos e classes todas que nao tem
18. tem muitas alteracoes vindas do origin/master e tem alteracoes locais faz o merge delas
