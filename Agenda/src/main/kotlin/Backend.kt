import kotlin.random.Random

class Note constructor(var title: String, var body: String, var id: Int, var imp: Boolean)

class AllNotes{
    //total list of notes in use
    var totalNotes = ArrayList<Note>()

    //
    var idVar = 0

    var activeNote = -1

    //add note function to create a new Note object when given parameters
    fun addNote(title: String, body: String, imp: Boolean) {
        var noteAdded = Note(title, body, idVar, imp)
        totalNotes.add(noteAdded)
        idVar ++
    }
    //random added notes
    fun addNoteRandom() {
        var random = Random.nextInt(1,5)

        var noteAdded = Note("Random Note", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla rhoncus nulla erat, sed volutpat lorem porta et. Suspendisse vel lobortis massa.", idVar, random==1)
        totalNotes.add(noteAdded)
        idVar ++
    }

    //edits note with info received
    fun editNote(idVar: Int, title: String, body: String, imp: Boolean){
        for(i in totalNotes){
            if(i.id == idVar){
                i.title = title
                i.body = body
                i.imp = imp
            }
        }
    }



    //deletes note from inputted id
    fun deleteNote(){
        for(i in totalNotes){
            if(i.id == activeNote){
                totalNotes.remove(i)
                return
            }
        }
    }

    //gets all notes specified by search bar and important toggle button
    fun getNotes(imp: Boolean, search: String): ArrayList<Note>{
        var result = ArrayList<Note>()
        for(i in totalNotes){
            if(imp == false || i.imp == true){
                if(i.title.contains(search) || i.body.contains((search))){
                    result.add(i)
                }
            }
        }

        return result
    }

    //clears all notes that are on screen
    fun clearNotes(imp: Boolean, search: String){

        var result = ArrayList<Note>()
        for(i in totalNotes){
            if(imp == false || i.imp == true){
                if(i.title.contains(search) || i.body.contains((search))){
                    result.add(i)
                }
            }
        }

        totalNotes.removeAll(result)
    }

}
