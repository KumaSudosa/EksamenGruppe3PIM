# EksamenGruppe3PIM

Gruppe:

Andreas, Cahit, Marcus & Michael


Før programmet kan køres:

-----------------------------------------------------------------------------

Det er nødvendigt at indsætte en fil (db.properties) under stien src/main/resources/db.properties.
Filen har brug for oplysninger til en virkende server med følgende kritirier:

user=

password=

url=jdbc:mysql://IP:PORT/DatabaseName

fakeurl=jdbc:mysql://IP:PORT/FakeDatabaseName


SQL script PIM_Database_Script skal køre på DatabaseName, og PIM_Test_Database_Script skal køre på FakeDatabaseName

SQL script PIM_Presentation_Script indeholder Mock objekter til fremvisning af program med PIM Objekter.

-----------------------------------------------------------------------------

Det er nødvendigt at indsætte en fil (cloudinary.properties) under stien src/main/resources/cloudinary.properties.
Filen har brug for oplysninger til en virkende cloudinary account (cloudinary.com) med følgende kritirier:

cloud_name=

api_key=

api_secret=

-----------------------------------------------------------------------------

Hvis programmet skal kunne køre på et linux system, er det nødvendigt at lave 2 mapper. Pictures og Json, i mappen for tomcat, catalina.base. Disse mapper bør være i samme mappe som mappen webapps.

Disse mapper har brug for tilladelse til at blive skrevet til og læst fra.
