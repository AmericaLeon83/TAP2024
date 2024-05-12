package com.example.demo.vistas;

import java.util.HashMap;
import java.util.Map;

public class IconManager {

    // Mapa para asociar nombres de bebidas con URL de iconos
    private static final Map<String, String> bebidaIconMap = new HashMap<>();
    private static final Map<String, String> desayunoIconMap = new HashMap<>();
    private static final Map<String, String> bocadilloIconMap = new HashMap<>();
    private static final Map<String, String> guarnicionIconMap = new HashMap<>();
    private static final Map<String, String> cafeIconMap = new HashMap<>();
    private static final Map<String, String> postreIconMap = new HashMap<>();
    private static final Map<String, String> snackTapasIconMap = new HashMap<>();
    private static final Map<String, String> platilloIconMap = new HashMap<>();

    // Inicializar el mapa con los nombres de bebidas y las URL de los iconos correspondientes
    static {
        bebidaIconMap.put("coca cola", "https://cdn-icons-png.flaticon.com/128/14369/14369808.png");
        bebidaIconMap.put("fanta", "https://cdn-icons-png.flaticon.com/128/14461/14461866.png");
        bebidaIconMap.put("pepsi", "https://cdn-icons-png.flaticon.com/128/14369/14369818.png");
        bebidaIconMap.put("jugo", "https://cdn-icons-png.flaticon.com/128/1047/1047473.png");
        bebidaIconMap.put("tea", "https://cdn-icons-png.flaticon.com/128/1764/1764316.png");
        bebidaIconMap.put("agua", "https://cdn-icons-png.flaticon.com/128/824/824239.png");
        bebidaIconMap.put("sprite", "https://cdn-icons-png.flaticon.com/128/7511/7511888.png");
        bebidaIconMap.put("cafe", "https://cdn-icons-png.flaticon.com/128/10847/10847468.png");
        // Agrega más bebidas según sea necesario

        desayunoIconMap.put("tostadas", "https://cdn-icons-png.flaticon.com/128/9099/9099882.png");
        desayunoIconMap.put("huevos revueltos", "https://cdn-icons-png.flaticon.com/128/3618/3618512.png");
        desayunoIconMap.put("panqueques", "https://cdn-icons-png.flaticon.com/128/6785/6785822.png");
        desayunoIconMap.put("cereal", "https://cdn-icons-png.flaticon.com/128/2829/2829840.png");
        desayunoIconMap.put("frutas", "https://cdn-icons-png.flaticon.com/128/3194/3194591.png");
        desayunoIconMap.put("yogur", "https://cdn-icons-png.flaticon.com/128/3142/3142859.png");

        bocadilloIconMap.put("bocadillo de jamon", "https://cdn-icons-png.flaticon.com/128/648/648680.png");
        bocadilloIconMap.put("bocadillo de queso", "https://cdn-icons-png.flaticon.com/128/3067/3067968.png");
        bocadilloIconMap.put("bocadillo de pollo", "https://cdn-icons-png.flaticon.com/128/15857/15857639.png");
        bocadilloIconMap.put("bocadillo vegetal", "https://cdn-icons-png.flaticon.com/128/5027/5027297.png");
        bocadilloIconMap.put("bocadillo de atun", "https://cdn-icons-png.flaticon.com/128/1923/1923479.png");
        bocadilloIconMap.put("bocadillo mixto", "https://cdn-icons-png.flaticon.com/128/5508/5508456.png");
        bocadilloIconMap.put("bocadillo de chorizo", "https://cdn-icons-png.flaticon.com/128/4781/4781488.png");
        bocadilloIconMap.put("bocadillo de salmon", "https://cdn-icons-png.flaticon.com/128/9622/9622165.png");
        // Agrega más bocadillos según sea necesario------------images aqui
        guarnicionIconMap.put("frijoles", "https://cdn-icons-png.flaticon.com/128/10441/10441297.png");
        guarnicionIconMap.put("arroz", "https://cdn-icons-png.flaticon.com/128/2714/2714041.png");
        guarnicionIconMap.put("ensalada", "https://cdn-icons-png.flaticon.com/128/1057/1057357.png");
        guarnicionIconMap.put("papas fritas", "https://cdn-icons-png.flaticon.com/128/1046/1046786.png");
        guarnicionIconMap.put("tortillas", "https://cdn-icons-png.flaticon.com/128/296/296605.png");
        guarnicionIconMap.put("guacamole", "https://cdn-icons-png.flaticon.com/128/4727/4727290.png");
        guarnicionIconMap.put("cebolla", "https://cdn-icons-png.flaticon.com/128/5346/5346247.png");
        guarnicionIconMap.put("maiz", "https://cdn-icons-png.flaticon.com/128/3944/3944286.png");
        // Agrega más guarniciones según sea necesario
        cafeIconMap.put("espresso", "https://cdn-icons-png.flaticon.com/128/2988/2988922.png");
        cafeIconMap.put("latte", "https://cdn-icons-png.flaticon.com/128/3354/3354187.png");
        cafeIconMap.put("cappuccino", "https://cdn-icons-png.flaticon.com/128/4822/4822291.png");
        cafeIconMap.put("americano", "https://cdn-icons-png.flaticon.com/128/7627/7627294.png");
        cafeIconMap.put("mocha", "https://cdn-icons-png.flaticon.com/128/1956/1956572.png");
        cafeIconMap.put("macchiato", "https://cdn-icons-png.flaticon.com/128/8433/8433050.png");
        cafeIconMap.put("frappuccino", "https://cdn-icons-png.flaticon.com/128/3715/3715504.png");
        cafeIconMap.put("iced coffee", "https://cdn-icons-png.flaticon.com/128/3754/3754648.png");
        // Agrega más cafés según sea necesario
        postreIconMap.put("pastel de chocolate", "https://cdn-icons-png.flaticon.com/128/5930/5930330.png");
        postreIconMap.put("flan", "https://cdn-icons-png.flaticon.com/128/6691/6691384.png");
        postreIconMap.put("helado", "https://cdn-icons-png.flaticon.com/128/3409/3409647.png");
        postreIconMap.put("tarta de manzana", "https://cdn-icons-png.flaticon.com/128/3657/3657039.png");
        postreIconMap.put("brownie", "https://cdn-icons-png.flaticon.com/128/13635/13635673.png");
        postreIconMap.put("galletas", "https://cdn-icons-png.flaticon.com/128/808/808783.png");
        postreIconMap.put("chocolate", "https://cdn-icons-png.flaticon.com/128/1375/1375229.png");
        postreIconMap.put("crema catalana", "https://cdn-icons-png.flaticon.com/128/10011/10011472.png");
        postreIconMap.put("tiramisu", "https://cdn-icons-png.flaticon.com/128/3863/3863573.png");
        postreIconMap.put("cheesecake", "https://cdn-icons-png.flaticon.com/128/6603/6603411.png");
        // Agrega más postres según sea necesario
        snackTapasIconMap.put("tacos", "https://cdn-icons-png.flaticon.com/128/8803/8803824.png");
        snackTapasIconMap.put("nachos", "https://cdn-icons-png.flaticon.com/128/362/362305.png");
        snackTapasIconMap.put("empanadas", "https://cdn-icons-png.flaticon.com/128/6040/6040518.png");
        snackTapasIconMap.put("croquetas", "https://cdn-icons-png.flaticon.com/128/7688/7688710.png");
        snackTapasIconMap.put("patatas bravas", "https://cdn-icons-png.flaticon.com/128/11247/11247507.png");
        snackTapasIconMap.put("calamares fritos", "https://cdn-icons-png.flaticon.com/128/11637/11637401.png");
        snackTapasIconMap.put("tortilla espanola", "https://cdn-icons-png.flaticon.com/128/2122/2122728.png");
        snackTapasIconMap.put("quesadillas", "https://cdn-icons-png.flaticon.com/128/9099/9099850.png");
        snackTapasIconMap.put("camarones", "https://cdn-icons-png.flaticon.com/128/909/909119.png");
        snackTapasIconMap.put("ensaladilla rusa", "https://cdn-icons-png.flaticon.com/128/7932/7932400.png");
        // Agrega más snacks o tapas según sea necesario
        platilloIconMap.put("Taco al Pastor", "https://cdn-icons-png.flaticon.com/128/6215/6215264.png");
        platilloIconMap.put("Taco de Carnitas", "https://cdn-icons-png.flaticon.com/128/11317/11317040.png");
        platilloIconMap.put("Taco de Barbacoa", "https://cdn-icons-png.flaticon.com/128/8512/8512378.png");
        platilloIconMap.put("Taco de Birria", "https://cdn-icons-png.flaticon.com/128/3946/3946536.png");
        platilloIconMap.put("Taco de Lengua", "https://cdn-icons-png.flaticon.com/128/8803/8803824.png");
        platilloIconMap.put("Taco de Pollo", "https://cdn-icons-png.flaticon.com/128/5787/5787272.png");
        platilloIconMap.put("Taco de Suadero", "https://cdn-icons-png.flaticon.com/128/9902/9902794.png");
        platilloIconMap.put("Taco de Nopal", "https://cdn-icons-png.flaticon.com/128/4372/4372203.png");
        platilloIconMap.put("Taco de Pescado", "https://cdn-icons-png.flaticon.com/128/11772/11772762.png");
        platilloIconMap.put("Taco de Camarón", "https://cdn-icons-png.flaticon.com/128/894/894861.png");
    }

    public static String getIconURL(String nombrePlato, String categoria) {
        String iconURL = null;
        switch (categoria) {
            case "bebida":
                iconURL = bebidaIconMap.get(nombrePlato);
                break;
            case "desayuno":
                iconURL = desayunoIconMap.get(nombrePlato);
                break;
            case "bocadillo":
                iconURL = bocadilloIconMap.get(nombrePlato);
                break;
            case "guarnicion":
                iconURL = guarnicionIconMap.get(nombrePlato);
                break;
            case "cafe":
                iconURL = cafeIconMap.get(nombrePlato);
                break;
            case "postre":
                iconURL = postreIconMap.get(nombrePlato);
                break;
            case "snackTapas":
                iconURL = snackTapasIconMap.get(nombrePlato);
                break;
            case "platillo":
                iconURL = platilloIconMap.get(nombrePlato);
                break;
            default:
                break;
        }
        if (iconURL == null) {
            // Devuelve una URL de icono predeterminada si no se encuentra ninguna
            iconURL = "https://cdn-icons-png.flaticon.com/128/894/894861.png";
        }
        return iconURL;
    }
}

