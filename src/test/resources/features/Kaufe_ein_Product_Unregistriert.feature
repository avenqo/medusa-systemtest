#language: de
Funktionalität: In meiner Rolle als unregistrierter Kunde möchte ich ein Produkt kaufen so dass ich es später geliefert bekommen kann

  Szenario: Ich kaufe ein einzelnes, nicht rabattiertes Produkt.
  	
  	Angenommen ich bin ein unregistrierter Kunde
  		| Vorname 	| Hartmut  			|
  		| Nachname 	| Koschinsko 		|
  		| Ort 			| Klecksersdorf |
  		| PLZ 			| 32154 				|
  		| Strasse 	| Sackgasse 12 	|
  		| E-Mail 		| hartmut@a.de 	|
  		
  	Und ich wähle Produkte aus dem Store und lege sie in den Warenkorb
  		| Artikelname 		|	Anzahl	| Color | Size	| Einzelpreis |
  		| Medusa T-Shirt	|	2				|	Black	| L			| €19.50			|
  		| Medusa Shorts 	|	1				|				| XL		| €25.00			|
  		
  	Und ich gehe zum Warenkorb
  	Und ich gehe zur Kasse
  	Und ich gebe meine minimalen Versandinformationen ein
  	Und ich wähle die Versandmethode "Standard"
  	
  	Wenn ich die Produkte kaufe
  	
  	Dann enthält die Bestellbestätigung meine Kundendaten und die Bestellnummer
  	Und die Bestellbestätigung enthält die Artikel 
  		| Name 			 		|	Anzahl	| Color | Size 	| Gesamtpreis |
  		| Medusa T-Shirt|	2				|	Black	|	L    	| €39.00			|
  		| Medusa Shorts |	1				|				|	XL		| €25.00			|
  	Und die Bestellbestätigung enthält in der Zusammenfassung
  	  | Teilkosten    | €64.00 | 
  	  | Versandkosten | €10.00 | 
  	  | Steuer 				| 			 | 
  	  | Gesamtkosten  | €74.00 | 
  	Und die Bezahlung der aktuellen Bestellung ist veranlasst
  	  | Gesamtbetrag    | 74.00            | 
  	  | Währung         | eur              | 
  	  | Payment Status  | awaiting         | 
  	  
  	  
  	  
  	