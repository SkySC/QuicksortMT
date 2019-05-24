# QuickSort Single Thread - Multi Thread

## Allgemeine Herausforderungen

Die allgemeingültige Aufgabe dieses Projektes besteht für jeden Teilnehmer darin, sich ein Problem zu suchen, welches Single Threaded als auch Multi-Threaded jeweils sinnvoll zu lösen ist.
Die seit Java 8 eingeführten Lambda-Funktionen sollen dabei miteinfließen, um das Verständnis des Teilnehmers aus der Vorlesung zu überprüfen.

## Das Thema des Projektes

Mit meiner Implementierung habe ich mich dem Quicksort Sortieralgorithmus angenommen.
Quicksort gehört zu den __divide-and-conquer__ Algorithmen.

### Allgemeine Funktionsweise

Dies bedeutet, dass die zu sortierende Folge anhand eines bestimmten Elementes, dem __Pivotelement__ in __n__ Teilfolgen aufgeteilt wird.
Im Anschluss werden diese __n__ Teilfolgen unabhängig voneinander meist rekursiv sortiert. Für jede rekursive Teilfolge wird ein neues Pivotelement bestimmt.
Ist man beim letzten rekursiven Schritt angekommen, werden alle bereits rekursiv sortierten Folgen aneinandergeknüpft.
Man erhält eine im Gesamtbild sortierte Folge.

### Das Pivotelement

Das Pivotelement bestimmt den Index an dem die Folgen jeweils in zunächst zwei Teilfolgen aufgetrennt werden.
Dabei wird der Index ```i = 0 bis i = pivotIndex - 1``` für die erste und ```i = pivotIndex + 1 bis i = folgenLaenge - 1``` für die zweite Teilfolge gewählt. Die Elemente zwischen den Indizes der ersten Teilfolge sind alle __kleiner oder gleich__ dem Pivotelement. Elemente zwischen den Indizes der zweiten Teilfolge __größer__.

__Die Wahl des Pivotelemnts__

- Wähle das __letzte Element__ der Folge als Pivotelement
- Wähle den __Median__ der Folge als Pivotelemnt
- Wähle ein __beliebiges Element__ der Folge als Pivotelement.

### Implementierungsweise

1. Wähle ein __Pivotelement__ für die aktuelle Folge.
2. __Ordne__ Element __kleiner oder gleich__ dem Pivotelement links und Elemente __größer__ rechts davon ein.

Das Pivotelement befindet sich nun bereits als sortiertes Element an der richtigen Stelle und muss nicht weiter betrachtet werden.

3. Wende die oberen beiden Schritte rekursiv an, bis die gesamte Folge sortiert ist.

### Essenzielle Methoden

- ```quicksort(Folge folge, int linkerIndex, int rechterIndex)```

Erhält die Folge, den linken Index als auch den rechten Index der Folge als Parameter.
Überprüft wird mittels ```ersterIndex >= letzterIndex```, ob bereits bis zur letzten Zahl durchsortiert wurde.
Ist dies der Fall, ist der Algorithmus bereits die gesamte Folge rekursiv durchgegangen bzw. die Folge sortiert. Der Algorithmus wird beendet.

Im Anschluss wird die ```partition()``` Methode aufgerufen, welche die aktuellen Parameter übergeben bekommt und den Index des Pivotelementes als Rückgabewert liefert.
Zuletzt wird die ```quicksort()``` Methode jeweils für beide Teilfolgen rekursiv aufgerufen.
Dabei enthält die linke Teilfolge die neuen Indizegrenzen von ```i = 0 bis i = pivotIndex - 1``` bzw für die rechte Teilfolge ```i = pivotIndex + 1 bis i = folgenLaenge - 1```. Diese grenzen die zu sortierenden Elemente auf die Elemente __kleiner, gleich bzw. größer__ als das Pivotelement ein.

- ```partition(Folge folge, int linkerIndex, int rechterIndex)```

1. Ermittle das __Pivotelement__ (letztes Element der Folge)

Initialisiere zwei nebenherlaufende Indizes ```ersterZeiger``` bzw. ```zweiterZeiger```. Sie verweisen auf die einzelnen Elemente der Folge und nützen beim Vertauschen der Elemente. Der ```ersteZeiger``` startet eine Position vor dem rechten Zeiger ```zweiterZeiger```.

2. Iteriere durch die Folge; vergleiche das Element auf das der ```erstenZeiger``` zeigt mit dem __Pivotelement__
2.1. Element ist __kleiner oder gleich__? Erhöhe den Index des __zweiten Zeigers__ und vertausche die Elemente beider Zeiger miteinander.
2.2. Element ist __größer__? Erhöhe den __ersten Zeiger__ solange, bis sich ein Element findet, welches __2.1.__ erfüllt bzw. bis zur Abbruchbedingung der Schleife (Abbruch ein Element vor dem Pivotelement).

3. Nach Beendigung der Iteration wird der __zweite Zeiger__ erhöht und mit dem __Pivotelement__ der Folge vertauscht.

Dadurch stehen alle Elemente __kleiner oder gleich__ dem Pivotelement links bzw. alle Elemente __größer__ rechts davon. Schlussendlich ist das Pivotelement somit einsortiert worden.

- ```vertauscheElemente(int ersterIndex, int zweiterIndex)```

Vertauscht die Elemente innerhalb der Folge miteinander auf die ```ersterIndex``` bzw. ```zweiterIndex``` jeweils zu einem Zeitpunkt zeigen.

### Komplexität des Sortieralgorithmus

Die durschnittliche Komplexität von Quicksort bei Wahl des letzten Elements als Pivotelement beträgt __O(n * log n)__.

Den Median der Folge als Pivotelemnt zu wählen bietet die beste Komplexität für den Quicksort-Algorithmus.
Der einfachheit halber habe ich mich für das letzte Element als Pivotelement entschieden.

## Quicksort Multi-Threaded

### Das ForkJoin-Framework

Das ForkJoin-Framework existiert seit Java 7. Es dient dazu das parallele Arbeiten effizienter und für den Entwickler einfacher zu gestalten. Dies tut es, in dem es ebenfalls nach dem __divide-and-conquer__ Prinzip arbeitet. Mit anderen Worten; Eine Gesamtaufgabe eines Prozesses wird in kleinste Subeinheiten unterteilt (__fork__), sprich maximal vereinfacht.

Diese Subeinheiten werden im Anschluss von den verfügbaren Threads (__worker__) unabhängig voneinander abgearbeitet.
Sind alle Subaufgaben erledigt, werden die Lösungen im Anschluss zusammengefügt (__join__).
Man erhält eine Lösung für das Gesamtproblem.

### Unterschiede zur Singe-Threaded Implementierung

- ```quicksort(Folge folge, int linkerIndex, int rechterIndex)```

Schritt __1.__ und __2.__ identisch.

Die Rekursion im __3.__ Schritt unterscheidet sich in der Implementierung.
Es werden jeweils zwei neue Folgen innerhalb der Methode gebildet. Sie erhalten wie oben beschrieben die veränderten Indizes als Grenzen erhalten, um die Elemente __kleiner oder gleich__ bzw. Elemente __größer__ als das Pivotelement zu spezifizieren.

Ist dies geschehen, wird zuerst die linke Teilfolge (Teilproblem) in den __ForkJoinPool__ geworfen. Der __Fork Join Pool__ stellt nichts anderes als eine Sammlung von einer spezifizierten Anzahl an Threads dar. Innerhalb dieses Thread Pools bedienen sich die __Thread-worker__ an den im Pool vorhandenen Problemen bzw. hier den zu abarbeitenden Folgen.

- ```partition()``` identisch zur Single Threaded Ausführung

### Effizienz des ForkJoin Frameworks

Die Effizienz des ForkJoinFrameworks kommt obendrauf dadurch zustande, dass sich die __Thread-worker__, sobald sie ihre Arbeit verrichtet haben gegenseitig Aufgaben abnehmen.

Das Framework fordert den Entwickler lediglich dazu auf die Methode ```compute()``` aus der Superklasse ```RecursiveAction``` zu überschreiben. Sie definiert die Instruktionen bei der Ausführung jedes einzelnen __Thread-workers__.

### Nutzen von Multi-Threading



## Der Nutzen von Lambdafunktionen

### Was sind Lambdafunktionen?

### Wie sind Lambdafunktionen aufgebaut?

### Wie verwendet man Lambdafunktionen?

### Was nützen Lambdafunktionen?
