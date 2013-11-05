package se.lth.gameofnature.questions;

//Skapade denna som tom bara för att kunna göra TaskMarker-klassen på ett logiskt sätt.
//Tänkte att denna klass kan vara en abstrakt super-klass som ärvs av de olika frågetyperna,
//Gör det lättare med implementeringen på kartan.
public abstract class Question {

	//Abstrakt metod att implementeras i subklasserna som starta aktiviteten som är rätt för subklassen.
	public abstract void startQuestionActivity();
}
