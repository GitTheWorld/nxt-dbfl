nxt-dbfl
========

Diese Sammlung von Java/NXC Dateien ist für ein Schul-Projekt erstellt worden.
Grundlegend wird für alles das Java Paket nxtPythonBridge verwendet,
es enthält einige Hilfsmittel zur Grafikausgabe 
und am wichtigsten eine Klasse um mithilfe der 
control.py die nxt-python Bibliothek zu verwenden.

Zwei Anwendungen  (jeweils ein Paket im java src Ordner 
sowie ein Ordner im onNxt Source Ordner) 
die diese verwenden sind ebenfalls dabei:
-Eine Segwayfernsterung für den HTWay mit dazugehöriger NXT-Software
-Ein Spiel für mehrere NXTs auf einem Feld mit anzeige auf dem PC


onPC --> fertiges Packet mit vorkompilierten jar Dateien und passender Konfiguration zum Steuern eines Segways oder der Feldsimulation.

src --> enthält den gesamten Java Source (hauptsächlich Bluetooth Kommunikation

onNXT --> enthält eine Steuerungssoftware für den NXT auf NXC, einmal für einen HTWay Segway und einmal zur Feldsimulation.

dependencies --> enthält gezippt alle Python abhängigkeiten

flashNXT(linux) --> enthält Firmware sowie kleine Skript zum Flashen eines NXT mit Hilfe einer vorinstallierten libnxt

presentations --> enthält Präsentation zum NXT im .odp Format

bin --> Kompilierte Klassen
