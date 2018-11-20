# ShoppingList

### Bemutatás   

Egy bevásárló lista jó és költséghatékony tud lenni. Természetesen csak akkor, ha ezt a
bevásárló listát otthon készítjük el és ragaszkodunk hozzá. A program egy modern
bevásárló listát valósít meg. Az ötlet a mindennapokból vett „elküldenek bevásárolni de
elfelejtem mit is kell venni” eseményekből adódik.

### Főbb funkciók   
Az alkalmazásban lehetőség van több lista készítésére, és törlésére. Egy listát kijelölve
megjeleníthető kategóriák szerint rendezve a listában tárolt elemek neve, ára. Ugyanitt
vehetjük fel a listához az elemeket. Ezeket az elemeket kijelölhetjük ami a „már benne van a
kosárban”-t jelenti. Az elemekre kattintva megjelenik a különböző adatai mint például a név,
ár, kategória, megjegyzés és kép.

#### Bővebben a listában tárolt elemekről:   
Hozzáadáskor ki kell kötelezően választani az adott kategóriát amibe az elem tartozik illetve
egy nevet megadni neki. Az ár, egy megjegyzés („20%-os helyett jó a 18%-os is”), illetve kép
beírása opcionális. Képet lehet a galériából választani illetve készíteni is, ha egy konkrét
márkának konkrét termékét szeretnénk.

### Megvalósítás   
A listák perzisztensen lesznek eltárolva a Sugar-ORM-el.
A listák és a listában tárolt elemekhez a RecycleView-t fogom használni.
Egyes listákhoz időpontokat is fel lehet venni emlékeztetőként, illetve meg is lehet osztani
őket Intent segítségével a különböző alkalmazások között.
