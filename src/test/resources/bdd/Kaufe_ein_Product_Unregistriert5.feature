#language: de
Funktionalität: In meiner Rolle als unregistrierter Kunde
        			 	möchte ich ein Produkt kaufen
        			 	so dass ich es später geliefert bekommen kann

  Szenario: Beim Kauf eines Produkts erhalte ich eine Bestätigung meiner Bestellung.
  	
  	Angenommen ich bin ein unregistrierter Kunde
  	Und ich lege ein Produkt in den Warenkorb
  	  | Artikel 			| Medusa Coffee Mug |           
  	  | Variante 			| One Size 					|
  	  | Anzahl  			| 2 								|
  	  | Einzelpreis  	| €11.99 						|
  	Und ich wähle eine Versandmethode
  		| Versandmethode	| Standard 	|           
  	  | Preis 					| €6.90 		|
  	
  	Wenn ich das Produkt kaufe
  	
  	Dann enthält die Bestellbestätigung meine Kundendaten
  	Und die Bestellbestätigung enthält die Artikel 
  		| Artikel           | Variante | Anzahl  | Preis  |
  		| Medusa Coffee Mug | One Size | 2       | €11.99 |
  		
  	Und die Bestellbestätigung enthält in der Zusammenfassung
  	  | Teilkosten    | €22.98 | 
  	  | Versandkosten | €6,90  | 
  	  | Gesamtkosten  | €29.88 | 
  	  
  	Und die Bezahlung der aktuellen Bestellung in Höhe von "€29.88 " ist veranlasst
  	  
  	   	  