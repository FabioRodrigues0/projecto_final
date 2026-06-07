package components;

import fabiorodrigues.bricks.components.Alert;
import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.FilePicker;
import fabiorodrigues.bricks.components.FileReader;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.style.Modifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class FormularioModal {

    private static final double DEFAULT_WIDTH = 500.0;
    private static final double DEFAULT_HEIGHT = 400.0;

    private final BricksApplication app;
    private final String windowTitle;
    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;

    private boolean update = false;
    private String createTitle = "Novo";
    private String editTitle = "Editar";
    private String cancelLabel = "Cancelar";
    private String createLabel = "Adicionar";
    private String updateLabel = "Atualizar";
    private Component content;
    private Runnable onSubmit = () -> {};
    private Runnable onClear = () -> {};
    private BiConsumer<File, String> onFileImport = null;

    public FormularioModal(BricksApplication app, String windowTitle) {
        this.app = app;
        this.windowTitle = windowTitle;
    }

    public FormularioModal size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public FormularioModal update(boolean update) {
        this.update = update;
        return this;
    }

    public FormularioModal createTitle(String createTitle) {
        this.createTitle = createTitle;
        return this;
    }

    public FormularioModal editTitle(String editTitle) {
        this.editTitle = editTitle;
        return this;
    }

    public FormularioModal titles(String createTitle, String editTitle) {
        this.createTitle = createTitle;
        this.editTitle = editTitle;
        return this;
    }

    public FormularioModal cancelLabel(String cancelLabel) {
        this.cancelLabel = cancelLabel;
        return this;
    }

    public FormularioModal createLabel(String createLabel) {
        this.createLabel = createLabel;
        return this;
    }

    public FormularioModal updateLabel(String updateLabel) {
        this.updateLabel = updateLabel;
        return this;
    }

    public FormularioModal content(Component content) {
        this.content = content;
        return this;
    }

    public FormularioModal onSubmit(Runnable onSubmit) {
        this.onSubmit = onSubmit != null ? onSubmit : () -> {};
        return this;
    }

    public FormularioModal onClear(Runnable onClear) {
        this.onClear = onClear != null ? onClear : () -> {};
        return this;
    }

    public FormularioModal onFileImport(BiConsumer<File, String> onFileImport) {
        this.onFileImport = onFileImport;
        return this;
    }

    public void show() {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(app.getStage());
        modal.initStyle(StageStyle.UNDECORATED);
        modal.setTitle(windowTitle);

        StackPane root = new StackPane();
        root.setPadding(new Insets(16));
        root.setPrefSize(width, height);

        State<File> importedFile = new State<>(null);
        FileReader fileReader = buildFileReader(importedFile);

        Runnable renderContent = () -> {
            root.getChildren().clear();
            Node node = buildBody(modal, importedFile, fileReader).render();
            root.getChildren().add(node != null ? node : new Pane());
        };
        renderContent.run();
        app.addRerenderListener(renderContent);
        modal
            .addEventHandler(
                WindowEvent.WINDOW_HIDDEN,
                event -> app.removeRerenderListener(renderContent)
            );

        modal.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> onClear.run());

        Scene scene = new Scene(root, width, height);
        applyBackdrop(modal);

        if (app.getStage() != null && app.getStage().getScene() != null) {
            scene.getStylesheets().addAll(app.getStage().getScene().getStylesheets());
        }

        modal.setScene(scene);
        modal.setResizable(false);
        modal.sizeToScene();
        modal.centerOnScreen();
        modal.show();
    }

    private Component buildBody(Stage modal, State<File> importedFile, FileReader fileReader) {
        Column column = new Column()
            .gap(8)
            .children(
                new Row()
                    .gap(8)
                    .modifier(new Modifier().alignment(Pos.CENTER_LEFT).fillMaxWidth())
                    .children(
                        new Text(update ? editTitle : createTitle).fontSize(18),
                        new Spacer(),
                        importButton(importedFile)
                    )
            );

        if (fileReader != null) {
            column.children(fileReader);
        }

        if (content != null) {
            column.children(content);
        }

        column
            .children(
                new Row()
                    .gap(8)
                    .modifier(new Modifier().alignment(Pos.BOTTOM_RIGHT))
                    .children(
                        new Button(cancelLabel).onClick(modal::close),
                        new Button(update ? updateLabel : createLabel).onClick(() -> {
                            try {
                                onSubmit.run();
                                modal.close();
                            } catch (RuntimeException e) {
                                if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                                    Alert.error("Erro", "Já existe um registo com esse valor.");
                                } else {
                                    Alert.error("Erro", "Não foi possível guardar o registo.");
                                }
                            }
                        })
                    )
            );

        return column;
    }

    private Component importButton(State<File> importedFile) {
        if (onFileImport == null) {
            return new Text("");
        }

        return new FilePicker()
            .label("Importar")
            .title("Importar documento")
            .filter("Documentos", "*.pdf", "*.txt", "*.md", "*.csv", "*.json", "*.xml")
            .filter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tif", "*.tiff")
            .bindTo(importedFile);
    }

    private FileReader buildFileReader(State<File> importedFile) {
        if (onFileImport == null) {
            return null;
        }

        return new FileReader().bindTo(importedFile).onText(content -> {
            printImportedContent("Texto", importedFile.get(), content);
            handleImportedContent(importedFile.get(), content);
        }).onPdf(content -> handlePdfContent(importedFile.get(), content)).onUnsupported(file -> {
            try {
                String ocrContent = readImageWithOcr(file);
                printImportedContent("OCR imagem", file, ocrContent);
                handleImportedContent(file, ocrContent);
            } catch (Exception e) {
                Alert.warning("Aviso", "Formato não suportado: " + file.getName());
            }
        }).onError(e -> Alert.warning("Aviso", "Não foi possível ler o ficheiro selecionado."));
    }

    private void handlePdfContent(File file, String content) {
        printImportedContent("PDFBox", file, content);

        if (!isWeakExtractedText(content)) {
            handleImportedContent(file, content);
            return;
        }

        try {
            String ocrContent = readPdfWithOcr(file);
            printImportedContent("OCR PDF", file, ocrContent);
            handleImportedContent(file, ocrContent);
        } catch (Exception e) {
            System.out.println("[FormularioModal] OCR PDF falhou: " + e.getMessage());
            handleImportedContent(file, content);
        }
    }

    private void handleImportedContent(File file, String content) {
        if (onFileImport == null || file == null) {
            return;
        }

        if (isWeakExtractedText(content)) {
            Alert.warning("Aviso", "Não foi encontrado texto no ficheiro: " + file.getName());
            return;
        }

        onFileImport.accept(file, content);
    }

    private void printImportedContent(String source, File file, String content) {
        System.out.println("----- " + source + " import: " + file.getAbsolutePath() + " -----");
        System.out.println(content == null ? "<null>" : content);
        System.out.println("----- fim " + source + " import -----");
    }

    private String readImageWithOcr(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IllegalArgumentException("Ficheiro não é uma imagem suportada.");
        }

        return buildTesseract().doOCR(image);
    }

    private String readPdfWithOcr(File file) throws Exception {
        StringBuilder content = new StringBuilder();

        try (PDDocument document = Loader.loadPDF(file)) {
            PDFRenderer renderer = new PDFRenderer(document);
            Tesseract tesseract = buildTesseract();

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = renderer.renderImageWithDPI(page, 300, ImageType.RGB);
                content.append(tesseract.doOCR(image)).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

    private Tesseract buildTesseract() {
        Tesseract tesseract = new Tesseract();
        resolveTessdataPath().ifPresent(tesseract::setDatapath);
        tesseract.setLanguage("por+eng");
        return tesseract;
    }

    private boolean isWeakExtractedText(String content) {
        if (content == null || content.isBlank()) {
            return true;
        }

        String meaningful = content
            .replaceAll("(?im)^\\s*(page|pagina|página|pag)\\s+\\d+\\s+(of|de)\\s+\\d+\\s*$", "")
            .replaceAll("(?m)^\\s*\\d+\\s*/\\s*\\d+\\s*$", "")
            .trim();

        return meaningful.length() < 20 || meaningful.split("\\s+").length < 4;
    }

    private java.util.Optional<String> resolveTessdataPath() {
        String configured = System.getProperty("tessdata.path");
        if (configured == null || configured.isBlank()) {
            configured = System.getenv("TESSDATA_PREFIX");
        }

        if (configured != null && !configured.isBlank() && Files.exists(Path.of(configured))) {
            return java.util.Optional.of(configured);
        }

        return java.util.stream.Stream
            .of(
                "config/tessdata",
                "tessdata",
                "/usr/share/tesseract-ocr/5/tessdata",
                "/usr/share/tesseract-ocr/4.00/tessdata",
                "/usr/share/tessdata"
            )
            .filter(path -> Files.exists(Path.of(path)))
            .findFirst();
    }

    private void applyBackdrop(Stage modal) {
        if (app.getStage() == null || app.getStage().getScene() == null) {
            return;
        }

        Node ownerRoot = app.getStage().getScene().getRoot();
        Effect previousEffect = ownerRoot.getEffect();

        ColorAdjust dim = new ColorAdjust();
        dim.setBrightness(-0.45);
        dim.setSaturation(-0.15);
        dim.setInput(previousEffect);

        ownerRoot.setEffect(dim);
        modal
            .addEventHandler(
                WindowEvent.WINDOW_HIDDEN,
                event -> ownerRoot.setEffect(previousEffect)
            );
    }
}
