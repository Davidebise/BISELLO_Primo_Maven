import com.github.lalyos.jfiglet.FigletFont;
import it.kamaladafrica.codicefiscale.City;
import it.kamaladafrica.codicefiscale.CodiceFiscale;
import it.kamaladafrica.codicefiscale.Person;
import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;
import net.datafaker.Faker;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import static java.lang.IO.println;
import static java.lang.IO.readln;

void main() throws IOException {
    Faker faker = new Faker();
    println(faker.chuckNorris().fact());
    println(faker.yoda().quote());
    String pokemon = faker.pokemon().name();
    println("è apparso un pokemon selvatico:"+pokemon);
    println(FigletFont.convertOneLine(pokemon));

    File tempFile= QRCode.from("https://www.youtube.com/watch?v=qIin9OFdnEs")
            .to(ImageType.PNG)
            .withSize(300,300)
            .file();
    File destinazione=new File("Qrcode.png");
   // Files.copy(tempFile.toPath(),destinazione.toPath());

    CityByName cities =
            CityProvider.ofDefault();

    City rome = cities.findByName("Roma");
// City rome = City.builder().name("ROMA").prov("RM").belfiore("H501").build();

    Person person =	Person.builder()
            .firstname(readln("Inserisci nome:"))
            .lastname(readln("Inserisci cognome:"))
            .birthDate(dataNascita())
            .isFemale(genere())
            .city(cities.findByName(readln("Inserisci città:")))
            .build();

    CodiceFiscale cf = CodiceFiscale.of(person);

    System.out.println(cf.getValue()); // RSSMRA75C22H501I

    println(CodiceFiscale.isFormatValid(cf.getValue()));
}

public static LocalDate dataNascita(){
    String data=readln("Inserisci data: gg/mm/aaaa");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(data, formatter);
}

public static boolean genere(){
    String genere="a";
    do {
        genere=readln("Inserisci genere: m/f");
    } while (!genere.equals("m") && !genere.equals("f"));
    return !genere.equals("m");
}