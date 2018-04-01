### PL: <br/> 

Program będący najbardziej podstawową implementacją sieci neuronowej. W głównym panelu program prosi o narysowanie dowolnej cyfry na planszy. Po kliknięciu odpowiedniego przycisku program zgaduje narysowaną cyfrę oraz przedstawia wyniki analizy na wykresie słupkowym. <br/>
<br/>
Sama sieć została napisana z pomocą youtubowego tutoriala autorstwa Finna Eggersa. Sieć jest oparta na perceptronach, czyli najbardziej podstawowych sztucznych neuronach. Sieć korzysta z funkcji sigmoidalnej. Stąd wynikają niedokładności w działaniu programu. Sieć została wytrenowana przy pomocy bazy danych MNIST, zawierających tysięce ręcznie narysowanych cyfr i ich liczbowych odpowiedników. Rozmiar sieci to 784(dane wejściowe) x 256 x 100('warstwy ukryte') x 10(dane wyjściowe). <br/>
<br/>
Uczenie siecie odbywa się za pomocą metod createSet()-tworzącej zestaw danych z wczytanych z bazy danych obrazków oraz trainData() - zmieniającej wartości 'weigths' i 'bias' sieci. Metody są częścią klasy Mnist. <br/>
<br/>
Program wykonany przy użyciu biblioteki JavaFX oraz wzorca projektowego Model-Widok-Kontroler. <br/>

[Video tutorial Finna Eggersa](https://www.youtube.com/watch?v=d3OtgsGcMLw) <br/>
[Baza danych MNIST](http://yann.lecun.com/exdb/mnist) <br/>

---

### ENG: <br/> 

This program is the most basic implementation of neural network. In main panel program asks user to draw a digit on a board. After pressing accurate button program guesses the drawn digit and presents the results of analysis on a chart. <br/>
<br/>
Actual neural network was created with help of youtube video tutorial by Finn Eggers. The network is using perceptrons, being the simplest type of artificial neurons. The network is using sigmoid function. That's why the program may not work properly. The network was trained by MNIST data base, containing thousands of hand-written digits and their digital equivalents. The size of the network is 784(input data) x 256 x 100('hidden layers') x 10(output data). <br/>
<br/>
Training of the neural network is proceeded by createSet() method - creating set of training data out of MNIST data base and trainData() - changing values of weights and biases of the network. The methods are parts of Mnist class. <br/>
<br/>
Program was created using JavaFX library and Model-View-Controller design pattern. <br/>

[Video tutorial by Finn Eggers](https://www.youtube.com/watch?v=d3OtgsGcMLw) <br/>
[MNIST data base](http://yann.lecun.com/exdb/mnist) <br/>
