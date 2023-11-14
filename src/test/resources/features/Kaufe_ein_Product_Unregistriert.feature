#language: de
Funktionalität: In meiner Rolle als unregistrierter Kunde
         möchte ich ein Produkt kaufen

  Szenario: Ich kaufe ein einzelnes, nicht rabattiertes Produkt.
  	
  	Angenommen ich bin ein unregistrierter Kunde
  	Und ich lege das Produkt "Medusa Coffee Mug" in den Warenkorb
  	Und ich gehe mit dem Warenkorb zur Kasse
  	Und ich gebe meine minimalen Versandinformationen ein
  	Und ich wähle die Versandmethode "Standard"
  	
  	Wenn ich das Produkt kaufe
  	
  	Dann sehe ich die Bestellbestätigung 
  	Und die Bestellbestätigung enthält meine Kundendaten
  	Und die Bestellbestätigung enthält die Artikel 
  		| Artikel           | Variante | Anzahl  | Preis  |
  		| Medusa Coffee Mug | One Size | 1       | €10.00 |
  		
  	Und die Bestellbestätigung enthält in der Zusammenfassung
  	  | Teilkosten    | €10.00 | 
  	  | Versandkosten | €10.00 | 
  	  | Gesamtkosten  | €20.00 | 
  	  
  	Und die Bezahlung der aktuellen Bestellung ist veranlasst
  	  | Gesamtbetrag    | 20.00            | 
  	  | Währung         | eur              | 
  	  | Payment Status  | awaiting         | 