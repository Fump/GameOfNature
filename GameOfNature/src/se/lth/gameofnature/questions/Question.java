package se.lth.gameofnature.questions;

//Skapade denna som tom bara f�r att kunna g�ra TaskMarker-klassen p� ett logiskt s�tt.
//T�nkte att denna klass kan vara en abstrakt super-klass som �rvs av de olika fr�getyperna,
//G�r det l�ttare med implementeringen p� kartan.
public abstract class Question {

	//Abstrakt metod att implementeras i subklasserna som starta aktiviteten som �r r�tt f�r subklassen.
	public abstract void startQuestionActivity();
}
