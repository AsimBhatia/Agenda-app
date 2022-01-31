import javafx.application.Application
import javafx.geometry.*
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.*
import javafx.scene.shape.*
import javafx.stage.Stage
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

class Main : Application() {

    override fun start(stage: Stage) {
        //instantiating AllNotes class
        val noteFunction = AllNotes()

        stage.title = "Agenda app"
        val minBoxLength = 400.0
        stage.minHeight = minBoxLength
        stage.minWidth = minBoxLength

        //top toolbar
        val addBtn = Button("Add")
        addBtn.prefWidth = 100.0
        val randomBtn = Button("Random")
        randomBtn.prefWidth = 100.0
        val deleteBtn = Button("Delete")
        deleteBtn.prefWidth = 100.0
        val clearBtn = Button("Clear")
        clearBtn.prefWidth = 100.0



        val impBtn = ToggleButton("!")



        val search = TextField()

        val topBar = HBox(10.0, addBtn, randomBtn, deleteBtn, clearBtn, impBtn, search)
        topBar.padding = Insets(10.0)
        //bottom window
        val count = Text("0")
        val task = Text("")

        val bottomBar = HBox(10.0, count, task)
        bottomBar.spacing = 40.0

        //flowpane code
        val app = FlowPane()
        app.hgap = 10.0
        app.vgap = 10.0
        app.padding = Insets(10.0)
//        app.background = Color.GRAY
        app.prefWidthProperty().bind(stage.widthProperty())
        app.prefHeightProperty().bind(stage.heightProperty())

//        for(i in 0..5) {
//            val testRectangle = Rectangle(150.0, 200.0)
//            testRectangle.fill = Color.WHITE
//            val title = Label("Title $i")
//            title.prefWidth = 150.0
//            val body = Label("Notes $i Execution failed for task Execution failed for taskExecution failed for taskExecution failed for taskExecution failed for taskExecution failed for task ':run'.\n")
//            body.prefWidth = 150.0
//            body.isWrapText = true
//            val noteBox = VBox(20.0, title, body)
//            val notes = StackPane(testRectangle, noteBox)
//            app.children.add(notes)
//        }
//        app.children.add(testRectangle)
        //window
        val pane = BorderPane()
        pane.center = app
        pane.bottom = bottomBar
        pane.top = topBar


//        val popupBoxBackground = Rectangle(400.0,200.0)
//        popupBoxBackground.fill = Color.GRAY
    //    val label = Label("Hello JavaFX")
//        label.font = Font("Helvetica", 14.0)
//        val scene = Scene(StackPane(label), 320.0, 240.0)
//        stage.title = "Main"
//        stage.scene = scene

        //redraws entire notes screen
        fun redraw() {
            app.children.clear()
            val displayNotes = noteFunction.getNotes(impBtn.isSelected, search.text)
            for(i in displayNotes){
                val testRectangle = Rectangle(150.0, 200.0)
                testRectangle.fill = Color.WHITE
                if(i.imp == true){
                    testRectangle.fill = Color.LIGHTYELLOW
                }
                val title = Label(i.title)
                title.prefWidth = 150.0
                val body = Label(i.body)
                body.prefWidth = 150.0
                body.isWrapText = true
                val noteBox = VBox(20.0, title, body)
                val notes = StackPane(testRectangle, noteBox)

                val noteEvent = EventHandler<MouseEvent> { mouseClick ->
                    if(mouseClick.clickCount == 1){
                        if(noteFunction.activeNote == i.id){
                            noteFunction.activeNote = -1
                        } else {
                            noteFunction.activeNote = i.id
                        }

                    }
                    redraw()
                }

                //check if note needs border (is selected)
                if(i.id == noteFunction.activeNote){
                    notes.border = Border(BorderStroke(Color.BLUE,BorderStrokeStyle.DOTTED,CornerRadii.EMPTY,BorderWidths.DEFAULT))
//
                }

                notes.addEventHandler(MouseEvent.MOUSE_CLICKED, noteEvent)
                app.children.add(notes)
            }
        }


        val scene = Scene(pane, 800.0, 600.0)
        scene.fill = Color.GRAY
        stage.scene = scene
        stage.show()

        clearBtn.setOnAction {
            noteFunction.clearNotes(impBtn.isSelected, search.text)
            redraw()
            count.text = "0"
        }

        randomBtn.setOnAction {
            noteFunction.addNoteRandom()
            task.text = "Added Note " + (noteFunction.idVar-1)
            var allNotes = noteFunction.getNotes(impBtn.isSelected, search.text)
            count.text = "" + allNotes.size
            redraw()

        }

        deleteBtn.setOnAction {
            if(noteFunction.activeNote != -1) {

                noteFunction.deleteNote()

                var allNotes = noteFunction.getNotes(impBtn.isSelected, search.text)
                count.text = "" + allNotes.size
                redraw()
            }
        }

        impBtn.setOnAction {
            redraw()
        }

        search.onKeyTyped = EventHandler { redraw() }
    }


}