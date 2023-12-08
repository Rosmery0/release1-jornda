package com.mmujer.jornada

import android.app.DatePickerDialog
import android.content.ClipData.Item
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import java.io.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val apiServicio = RetrofitClient.obtenerRetrofit().create(ApiServicio::class.java)
    var fecha = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Elementos del formulario
        val autoCompleteTextViewProvincia: AutoCompleteTextView = findViewById(R.id.auto_complete_txt_provincia)
        val autoCompleteTextViewMunicipio: AutoCompleteTextView = findViewById(R.id.auto_complete_txt_municipio)
        val autoCompleteTextViewDistrito: AutoCompleteTextView = findViewById(R.id.distrito_txt)
        val autoCompleteTextViewSector: AutoCompleteTextView = findViewById(R.id.sector_txt)
        val autoCompleteTextViewFecha: AutoCompleteTextView = findViewById(R.id.fecha_txt)
        val autoCompleteTextViewCoordinador: AutoCompleteTextView = findViewById(R.id.coordinador)
        val autoCompleteTextViewEditor: AutoCompleteTextView = findViewById(R.id.editor_txt)
        val autoCompleteTextViewHogares: AutoCompleteTextView = findViewById(R.id.hogares_txt)
        val autoCompleteTextViewMujeresH: AutoCompleteTextView = findViewById(R.id.mujeresH_txt)
        val autoCompleteTextViewHombresH: AutoCompleteTextView = findViewById(R.id.hombresH_txt)
        val autoCompleteTextViewComerciales: AutoCompleteTextView = findViewById(R.id.comerciales_txt)
        val autoCompleteTextViewImpactadasC: AutoCompleteTextView = findViewById(R.id.impactadasC_txt)
        val autoCompleteTextViewEducativos: AutoCompleteTextView = findViewById(R.id.educativos_txt)
        val autoCompleteTextViewImpactadasE: AutoCompleteTextView = findViewById(R.id.impactadasE_txt)
        val autoCompleteTextViewOtros: AutoCompleteTextView = findViewById(R.id.otros_txt)
        val autoCompleteTextViewImpactadasO: AutoCompleteTextView = findViewById(R.id.impactadasO_txt)
        val autoCompleteTextViewTalleres: AutoCompleteTextView = findViewById(R.id.talleres_txt)
        val autoCompleteTextViewMujeresT: AutoCompleteTextView = findViewById(R.id.mujeresT_txt)
        val autoCompleteTextViewHombresT: AutoCompleteTextView = findViewById(R.id.hombresT_txt)
        val buttonFecha: ImageButton = findViewById(R.id.imageButtonFecha)
        val buttonGuardar: Button = findViewById(R.id.btn_guardar)
        val buttonSubir: Button = findViewById(R.id.btn_subir)
        // Lista de provincias
        var Provincias = listOf(
            Provincia(1, "DISTRITO NACIONAL", 1, "D"),
            Provincia(2, "LA ALTAGRACIA", 28, "E"),
            Provincia(3, "AZUA", 10, "S"),
            Provincia(4, "BAHORUCO", 22, "S"),
            Provincia(5, "BARAHONA", 18, "S"),
            Provincia(6, "DAJABON", 44, "N"),
            Provincia(7, "DUARTE", 56, "N"),
            Provincia(8, "EL SEIBO", 25, "E"),
            Provincia(9, "ELIAS PIÑA", 16, "S"),
            Provincia(10, "ESPAILLAT", 54, "N"),
            Provincia(11, "HATO MAYOR", 27, "E"),
            Provincia(12, "INDEPENDENCIA", 77, "S"),
            Provincia(13, "LA ROMANA", 26, "E"),
            Provincia(14, "LA VEGA", 47, "N"),
            Provincia(15, "MARIA TRINIDAD SANCHEZ", 71, "N"),
            Provincia(16, "MONSEÑOR NOUEL", 48, "N"),
            Provincia(17, "MONTE CRISTI", 41, "N"),
            Provincia(18, "MONTE PLATA", 8, "E"),
            Provincia(19, "PEDERNALES", 69, "S"),
            Provincia(20, "PERAVIA", 3, "S"),
            Provincia(21, "PUERTO PLATA", 37, "N"),
            Provincia(22, "HERMANAS MIRABAL", 55, "N"),
            Provincia(23, "SAMANA", 65, "N"),
            Provincia(24, "SAN CRISTOBAL", 2, "S"),
            Provincia(25, "SAN JUAN", 12, "S"),
            Provincia(26, "SAN PEDRO DE MACORIS", 23, "E"),
            Provincia(27, "SANCHEZ RAMIREZ", 49, "N"),
            Provincia(28, "SANTIAGO", 31, "N"),
            Provincia(29, "SANTIAGO RODRIGUEZ", 46, "N"),
            Provincia(30, "VALVERDE", 34, "N"),
            Provincia(31, "SAN JOSE DE OCOA", 13, "S"),
            Provincia(32, "SANTO DOMINGO", 223, "D")
        )
        // Lista de todos los municipios
        val municipios = listOf(
            Municipio(1, "DISTRITO NACIONAL", 1, 1),
            Municipio(2, "SAN CRISTOBAL", 24, 2),
            Municipio(3, "BANI", 20, 3),
            Municipio(4, "BAYAGUANA", 18, 4),
            Municipio(5, "YAMASA", 18, 5),
            Municipio(8, "MONTE PLATA", 18, 8),
            Municipio(10, "AZUA", 3, 10),
            Municipio(11, "LAS MATAS DE FARFAN", 25, 11),
            Municipio(12, "SAN JUAN DE LA MAGUANA", 25, 12),
            Municipio(13, "SAN JOSE DE OCOA", 31, 13),
            Municipio(14, "EL CERCADO", 25, 14),
            Municipio(15, "BANICA", 9, 15),
            Municipio(16, "COMENDADOR", 9, 16),
            Municipio(17, "PADRE LAS CASAS", 3, 17),
            Municipio(18, "BARAHONA", 5, 18),
            Municipio(19, "CABRAL", 5, 19),
            Municipio(20, "DUVERGE", 12, 20),
            Municipio(21, "ENRIQUILLO", 5, 21),
            Municipio(22, "NEYBA", 4, 22),
            Municipio(23, "SAN PEDRO DE MACORIS", 26, 23),
            Municipio(24, "LOS LLANOS", 26, 24),
            Municipio(25, "EL SEIBO", 8, 25),
            Municipio(26, "LA ROMANA", 13, 26),
            Municipio(27, "HATO MAYOR", 11, 27),
            Municipio(28, "HIGUEY", 2, 28),
            Municipio(29, "MICHES", 8, 29),
            Municipio(30, "RAMON SANTANA", 26, 30),
            Municipio(31, "SANTIAGO DE LOS CABALLEROS", 28, 31),
            Municipio(32, "TAMBORIL", 28, 32),
            Municipio(33, "ESPERANZA", 30, 33),
            Municipio(34, "MAO", 30, 34),
            Municipio(35, "JANICO", 28, 35),
            Municipio(36, "SAN JOSE DE LAS MATAS", 28, 36),
            Municipio(37, "PUERTO PLATA", 21, 37),
            Municipio(38, "IMBERT", 21, 38),
            Municipio(39, "ALTAMIRA", 21, 39),
            Municipio(40, "LUPERON", 21, 40),
            Municipio(41, "MONTECRISTI", 17, 41),
            Municipio(42, "MONCION", 29, 42),
            Municipio(43, "RESTAURACION", 6, 43),
            Municipio(44, "DAJABON", 6, 44),
            Municipio(45, "GUAYUBIN", 17, 45),
            Municipio(46, "SAN IGNACIO DE SABANETA", 29, 46),
            Municipio(47, "LA VEGA", 14, 47),
            Municipio(48, "BONAO", 16, 48),
            Municipio(49, "COTUI", 27, 49),
            Municipio(50, "JARABACOA", 14, 50),
            Municipio(51, "VILLA TAPIA", 22, 51),
            Municipio(52, "CEVICOS", 27, 52),
            Municipio(53, "CONSTANZA", 14, 53),
            Municipio(54, "MOCA", 10, 54),
            Municipio(55, "SALCEDO", 22, 55),
            Municipio(56, "SAN FRANCISCO DE MACORIS", 7, 56),
            Municipio(57, "PIMENTEL", 7, 57),
            Municipio(58, "VILLA RIVA", 7, 58),
            Municipio(59, "CASTILLO", 7, 59),
            Municipio(60, "CABRERA", 15, 60),
            Municipio(61, "GASPAR HERNANDEZ", 10, 61),
            Municipio(63, "EUGENIO MARIA DE HOSTOS", 7, 63),
            Municipio(64, "TENARES", 22, 64),
            Municipio(65, "SAMANA", 23, 65),
            Municipio(66, "SANCHEZ", 23, 66),
            Municipio(67, "SABANA DE LA MAR", 11, 67),
            Municipio(68, "VILLA ALTAGRACIA", 24, 68),
            Municipio(69, "PEDERNALES", 19, 69),
            Municipio(70, "LA DESCUBIERTA", 12, 70),
            Municipio(71, "NAGUA", 15, 71),
            Municipio(72, "VILLA VASQUEZ", 17, 72),
            Municipio(73, "LOMA DE CABRERA", 6, 73),
            Municipio(74, "PEDRO SANTANA", 9, 74),
            Municipio(75, "HONDO VALLE", 9, 75),
            Municipio(76, "TAMAYO", 4, 76),
            Municipio(77, "JIMANI", 12, 77),
            Municipio(78, "VILLA JARAGUA", 4, 78),
            Municipio(79, "VICENTE NOBLE", 5, 79),
            Municipio(80, "PARAISO", 5, 80),
            Municipio(81, "RIO SAN JUAN", 15, 81),
            Municipio(82, "YAGUATE", 24, 82),
            Municipio(83, "SABANA GRANDE DE PALENQUE", 24, 83),
            Municipio(84, "NIZAO", 20, 84),
            Municipio(85, "SAN RAFAEL DEL YUMA", 2, 85),
            Municipio(86, "PEPILLO SALCEDO", 17, 86),
            Municipio(87, "FANTINO", 27, 87),
            Municipio(88, "CAYETANO GERMOSEN", 10, 88),
            Municipio(89, "JOSE CONTRERAS (DM)", 10, 54),
            Municipio(90, "SABANA GRANDE DE BOYA", 18, 90),
            Municipio(91, "OVIEDO", 19, 91),
            Municipio(92, "LAGUNA SALADA", 30, 92),
            Municipio(93, "BAJOS DE HAINA", 24, 93),
            Municipio(94, "VILLA GONZALEZ", 28, 94),
            Municipio(95, "LICEY AL MEDIO", 28, 95),
            Municipio(96, "VILLA BISONO -NAVARRETE-", 28, 96),
            Municipio(97, "SOSUA", 21, 97),
            Municipio(99, "POSTRER RIO", 12, 99),
            Municipio(100, "EL VALLE", 11, 100),
            Municipio(101, "CASTAÑUELAS", 17, 101),
            Municipio(102, "LOS HIDALGOS", 21, 102),
            Municipio(103, "GUAYMATE", 13, 103),
            Municipio(104, "CAMBITA GARABITOS", 24, 104),
            Municipio(105, "GUAYABAL", 3, 105),
            Municipio(106, "PERALTA", 3, 106),
            Municipio(107, "SABANA YEGUA", 3, 107),
            Municipio(108, "VALLEJUELO", 25, 108),
            Municipio(109, "BOHECHIO", 25, 109),
            Municipio(110, "EL LLANO", 9, 110),
            Municipio(111, "POLO", 5, 111),
            Municipio(112, "LOS RIOS", 4, 112),
            Municipio(113, "GALVAN", 4, 113),
            Municipio(114, "MELLA", 12, 114),
            Municipio(115, "PARTIDO", 6, 115),
            Municipio(116, "VILLA LOS ALMACIGOS", 29, 116),
            Municipio(117, "LAS MATAS DE SANTA CRUZ", 17, 117),
            Municipio(118, "MAIMON", 16, 118),
            Municipio(119, "ARENOSO", 7, 119),
            Municipio(120, "GUANANICO", 21, 120),
            Municipio(121, "VILLA ISABELA", 21, 121),
            Municipio(122, "JIMA ABAJO", 14, 122),
            Municipio(123, "PIEDRA BLANCA", 16, 123),
            Municipio(124, "LA CUEVA (DM)", 27, 52),
            Municipio(125, "LAS YAYAS DE VIAJAMA", 3, 125),
            Municipio(126, "TABARA ARRIBA", 3, 126),
            Municipio(127, "UVILLA (DM)", 4, 76),
            Municipio(128, "CRISTOBAL", 12, 128),
            Municipio(129, "JUAN DE HERRERA", 25, 129),
            Municipio(130, "EL PEÑON", 5, 130),
            Municipio(131, "FUNDACION", 5, 131),
            Municipio(132, "ESTEBANIA", 3, 132),
            Municipio(133, "JAMAO AL NORTE", 10, 133),
            Municipio(134, "LAS TERRENAS", 23, 134),
            Municipio(135, "LAS CHARCAS", 3, 135),
            Municipio(136, "EL FACTOR", 15, 136),
            Municipio(137, "LAS SALINAS", 5, 137),
            Municipio(138, "CONSUELO", 26, 138),
            Municipio(139, "LOS CACAOS", 24, 139),
            Municipio(140, "SAN GREGORIO DE NIGUA", 24, 140),
            Municipio(141, "LA LAGUNA DE NISIBON (DM)", 2, 28),
            Municipio(142, "PEDRO GARCIA (DM)", 28, 31),
            Municipio(143, "LAS GUARANAS", 7, 143),
            Municipio(144, "JUAN SANTIAGO", 9, 144),
            Municipio(145, "LA OTRA BANDA (DM)", 2, 28),
            Municipio(146, "QUISQUEYA", 26, 146),
            Municipio(147, "DON JUAN (DM)", 18, 8),
            Municipio(148, "SABANA IGLESIA", 28, 148),
            Municipio(149, "SAN VICTOR", 10, 149),
            Municipio(150, "SABANA LARGA", 31, 150),
            Municipio(151, "EL PINO", 6, 151),
            Municipio(152, "RANCHO ARRIBA", 31, 152),
            Municipio(153, "PERALVILLO", 18, 153),
            Municipio(154, "PUEBLO VIEJO", 3, 154),
            Municipio(155, "VILLA LA MATA", 27, 155),
            Municipio(156, "MATANZAS", 20, 156),
            Municipio(157, "VILLA FUNDACION (DM)", 20, 3),
            Municipio(158, "SABANA BUEY (DM)", 20, 3),
            Municipio(159, "RIO LIMPIO (DM)", 9, 74),
            Municipio(160, "LA CIENAGA", 5, 160),
            Municipio(161, "BAITOA", 28, 161),
            Municipio(162, "JOBA ARRIBA (DM)", 10, 61),
            Municipio(163, "TIREO ARRIBA (DM)", 14, 53),
            Municipio(164, "CANOA (DM)", 5, 79),
            Municipio(165, "SAN JOSE DE MATANZAS (DM)", 15, 71),
            Municipio(166, "PEDRO SANCHEZ (DM)", 8, 25),
            Municipio(167, "JAQUIMEYES", 5, 167),
            Municipio(168, "YERBA BUENA (DM)", 11, 27),
            Municipio(169, "ELUPINA CORDERO DE LAS CAÑITAS (DM)", 11, 67),
            Municipio(170, "PIZARRETE (DM)", 20, 84),
            Municipio(171, "VILLA ELISA (DM)", 17, 45),
            Municipio(172, "LA CANELA (DM)", 28, 31),
            Municipio(173, "HATILLO PALMA (DM)", 17, 45),
            Municipio(174, "GONZALO (DM)", 18, 90),
            Municipio(175, "VILLA MONTELLANO", 21, 175),
            Municipio(176, "SANTANA (DM)", 20, 84),
            Municipio(177, "ESTERO HONDO (DM)", 21, 121),
            Municipio(178, "GUAYABAL (DM)", 12, 99),
            Municipio(179, "LOS BOTADOS (DM)", 18, 5),
            Municipio(180, "LA CIENAGA (DM)", 31, 13),
            Municipio(181, "RIO VERDE ARRIBA (DM)", 14, 47),
            Municipio(182, "AGUA SANTA DEL YUNA (DM)", 7, 58),
            Municipio(183, "CRISTO REY DE GUARAGUAO (DM)", 7, 58),
            Municipio(184, "JUANCHO (DM)", 19, 91),
            Municipio(185, "PAYA (DM)", 20, 3),
            Municipio(186, "MATAYAYA (DM)", 25, 11),
            Municipio(187, "RINCON (DM)", 14, 122),
            Municipio(188, "LA ISABELA (DM)", 21, 40),
            Municipio(189, "LA PEÑA (DM)", 7, 56),
            Municipio(190, "PEDRO CORTO (DM)", 25, 12),
            Municipio(191, "EL RUBIO (DM)", 28, 36),
            Municipio(192, "JUNCALITO (DM)", 28, 35),
            Municipio(193, "BLANCO (DM)", 22, 64),
            Municipio(194, "AMINA (DM)", 30, 34),
            Municipio(195, "JAIBON (DM)", 30, 34),
            Municipio(196, "GUATAPANAL (DM)", 30, 34),
            Municipio(197, "MAIZAL (DM)", 30, 33),
            Municipio(198, "EL CACHON (DM)", 5, 18),
            Municipio(199, "VILLA SONADOR (DM)", 16, 123),
            Municipio(200, "EL PUERTO (DM)", 26, 24),
            Municipio(201, "SABANA DEL PUERTO (DM)", 16, 48),
            Municipio(202, "MATA PALACIO (DM)", 11, 27),
            Municipio(203, "ARROYO SALADO (DM)", 15, 60),
            Municipio(204, "JICOME (DM)", 30, 33),
            Municipio(205, "CENOVI (DM)", 7, 56),
            Municipio(207, "PALMAR DE OCOA (DM)", 3, 135),
            Municipio(208, "JUAN LOPEZ ABAJO (EL MAMEY) (DM)", 10, 54),
            Municipio(209, "MAJAGUAL (DM)", 18, 90),
            Municipio(210, "JUAN ADRIAN (DM)", 16, 123),
            Municipio(211, "BOCA DE YUMA (DM)", 2, 85),
            Municipio(212, "LA ENTRADA (DM)", 15, 60),
            Municipio(213, "EL POZO (DM)", 15, 136),
            Municipio(214, "EL PALMAR (DM)", 4, 22),
            Municipio(215, "GUAYABO DULCE (DM)", 11, 27),
            Municipio(216, "PESCADERIA (DM)", 5, 131),
            Municipio(217, "JAIBÓN (DM)", 30, 92),
            Municipio(218, "EL LIMON (DM)", 23, 65),
            Municipio(219, "PALMAR ARRIBA (DM)", 28, 94),
            Municipio(220, "LA BIJA (DM)", 27, 155),
            Municipio(221, "VILLA SOMBRERO (DM)", 20, 3),
            Municipio(222, "LOS PATOS (DM)", 5, 80),
            Municipio(223, "SANTO DOMINGO ESTE", 32, 223),
            Municipio(224, "SANTO DOMINGO OESTE", 32, 224),
            Municipio(225, "SANTO DOMINGO NORTE", 32, 225),
            Municipio(226, "BOCA CHICA", 32, 226),
            Municipio(227, "SAN ANTONIO DE GUERRA", 32, 227),
            Municipio(228, "PEDRO BRAND", 32, 228),
            Municipio(229, "LOS ALCARRIZOS", 32, 229),
            Municipio(230, "LA VICTORIA (DM)", 32, 225),
            Municipio(231, "VILLARPANDO (DM)", 3, 125),
            Municipio(232, "LA CAYA (DM)", 30, 92),
            Municipio(233, "ANGELINA (DM)", 27, 155),
            Municipio(234, "EL CARRIL (DM)", 24, 93),
            Municipio(235, "EL CARRETON (DM)", 20, 3),
            Municipio(236, "LAS LAGUNAS ABAJO (DM)", 10, 54),
            Municipio(237, "EL JOVERO (EL CEDRO) (DM)", 8, 29),
            Municipio(238, "EL LIMON (DM)", 12, 77),
            Municipio(239, "SAN FRANCISCO DE JACAGUA (DM)", 28, 31),
            Municipio(240, "VERAGUA (DM)", 10, 61),
            Municipio(241, "CANA CHAPETON (DM)", 17, 45),
            Municipio(242, "MANUEL BUENO (DM)", 6, 151),
            Municipio(243, "BELLOSO (DM)", 21, 40),
            Municipio(244, "CABARETE (DM)", 21, 97),
            Municipio(245, "SABANETA DE YASICA (DM)", 21, 97),
            Municipio(246, "EL LIMON (DM)", 28, 94),
            Municipio(247, "GAUTIER (DM)", 26, 24),
            Municipio(248, "CRUCE DE GUAYACANES (DM)", 30, 92),
            Municipio(249, "BAYAHIBE (DM)", 2, 85),
            Municipio(250, "LA SABINA (DM)", 14, 53),
            Municipio(251, "EL RANCHITO (DM)", 14, 47),
            Municipio(252, "SAN JOSE-PINO HERRADO-EL PUERTO(DM)", 24, 68),
            Municipio(253, "PALO VERDE (EL AHOGADO) (DM)", 17, 101),
            Municipio(254, "CHIRINO (DM)", 18, 8),
            Municipio(255, "MEDINA (DM)", 24, 68),
            Municipio(256, "LA CUCHILLA (DM)", 24, 68),
            Municipio(257, "LOS TOROS (DM)", 3, 126),
            Municipio(258, "HATO DEL YAQUE (DM)", 28, 31),
            Municipio(259, "PROYECTO #4 (DM)", 3, 107),
            Municipio(260, "PROYECTO D-1 GANADERO (DM)", 3, 107),
            Municipio(261, "EL ROSARIO (DM)", 3, 154),
            Municipio(262, "ARROYO BARRIL (DM)", 23, 65),
            Municipio(263, "LA JAIBA (DM)", 21, 121),
            Municipio(264, "LAS BARIAS-LA ESTANCIA (DM)", 3, 10),
            Municipio(265, "LAS TARANAS (DM)", 7, 58),
            Municipio(266, "LAS COLES (DM)", 7, 119),
            Municipio(267, "LOS JOVILLOS (DM)", 3, 10),
            Municipio(268, "LAS CUESTA (DM)", 28, 36),
            Municipio(269, "LAS PLACETAS (DM)", 28, 36),
            Municipio(270, "MONTE LLANO (JAMAO AFUERA) (DM)", 22, 55),
            Municipio(271, "LAS GORDAS (DM)", 15, 71),
            Municipio(272, "QUITA CORAZA (DM)", 5, 79),
            Municipio(273, "HIGUERITO (DM)", 10, 54),
            Municipio(274, "MONTE DE LA JAGUA (DM)", 10, 54),
            Municipio(275, "ORTEGA (DM)", 10, 54),
            Municipio(276, "BARRO ARRIBA (DM)", 3, 10),
            Municipio(277, "NIZAO-LAS AUYAMAS (DM)", 31, 13),
            Municipio(278, "EL PINAR (DM)", 31, 13),
            Municipio(279, "FONDO NEGRO (DM)", 5, 79),
            Municipio(280, "SABANA LARGA (DM)", 9, 16),
            Municipio(281, "JUMA BEJUCAL (DM)", 16, 48),
            Municipio(282, "AMIAMA GOMEZ (DM)", 3, 126),
            Municipio(283, "TABARA ABAJO (DM)", 3, 126),
            Municipio(284, "ARROYO DULCE (DM)", 5, 21),
            Municipio(285, "CANCA LA REYNA (DM)", 10, 54),
            Municipio(286, "LA CALETA (DM)", 32, 226),
            Municipio(287, "ARROYO CANO (DM)", 25, 109),
            Municipio(288, "LAS CLAVELLINAS (DM)", 4, 112),
            Municipio(289, "SANTANA (DM)", 4, 76),
            Municipio(290, "SABANA GRANDE (DM)", 7, 63),
            Municipio(291, "SAN LUIS (DM)", 32, 223),
            Municipio(292, "SABANETA (DM)", 25, 12),
            Municipio(293, "LA COLONIA (DM)", 12, 114),
            Municipio(294, "PALO ALTO (DM)", 5, 167),
            Municipio(295, "VILLA HERMOSA", 13, 295),
            Municipio(296, "CUMAYASA (DM)", 13, 295),
            Municipio(297, "YAQUE (BUENA VISTA) (DM)", 25, 109),
            Municipio(298, "MONSERRAT (DM)", 4, 76),
            Municipio(299, "BOYA-EL CENTRO (DM)", 18, 8),
            Municipio(300, "BUENA VISTA (DM)", 14, 50),
            Municipio(301, "BAHORUCO (DM)", 5, 160),
            Municipio(302, "LA CATALINA (DM)", 20, 3),
            Municipio(303, "LAS LAGUNAS (DM)", 3, 17),
            Municipio(304, "LA SIEMBRA (DM)", 3, 17),
            Municipio(305, "NAVAS (DM)", 21, 102),
            Municipio(306, "HATO DAMAS (DM)", 24, 2),
            Municipio(307, "HATO VIEJO (DM)", 32, 227),
            Municipio(308, "EL CAIMITO (DM)", 28, 35),
            Municipio(309, "PLATANAL (DM)", 27, 49),
            Municipio(310, "LA GUAYIGA (DM)", 32, 228),
            Municipio(311, "LA CUABA (DM)", 32, 228),
            Municipio(312, "PALMAREJO (DM)", 32, 229),
            Municipio(313, "PANTOJA (DM)", 32, 229),
            Municipio(314, "SABANA ALTA (DM)", 25, 12),
            Municipio(315, "CAMBITA EL PUEBLECITO (DM)", 24, 104),
            Municipio(316, "BOCA DE CACHON (DM)", 12, 77),
            Municipio(317, "DERRUMBADERO (EL NUEVO BRAZIL) (DM)", 25, 14),
            Municipio(318, "CAPOTILLO (DM)", 6, 73),
            Municipio(319, "EL ROSARIO (DM)", 25, 12),
            Municipio(320, "QUITA SUEÑO (DM)", 27, 49),
            Municipio(321, "CAÑONGO (DM)", 6, 44),
            Municipio(322, "LAS GALERAS (DM)", 23, 65),
            Municipio(323, "VENGAN A VER (DM)", 12, 20),
            Municipio(324, "LA GINA (DM)", 8, 29),
            Municipio(325, "JOSE FRANCISCO PEÑA GOMEZ (DM)", 19, 69),
            Municipio(326, "HATO DEL PADRE (DM)", 25, 12),
            Municipio(327, "LAS PALOMAS (DM)", 28, 95),
            Municipio(328, "BOCA DE MAO (DM)", 30, 33),
            Municipio(329, "BATISTA (DM)", 25, 14),
            Municipio(330, "CARRERA DE YEGUAS (DM)", 25, 11),
            Municipio(331, "GUANITO (DM)", 25, 12),
            Municipio(332, "YASICA ARRIBA (DM)", 21, 37),
            Municipio(333, "CABEZA DE TORO (DM)", 4, 76),
            Municipio(334, "PUERTO VIEJO-LOS NEGROS (DM)", 3, 10),
            Municipio(335, "BARRERAS (DM)", 3, 10),
            Municipio(336, "LA JAGUA (DM)", 25, 12),
            Municipio(337, "GUANITO (DM)", 9, 110),
            Municipio(338, "SABANA CRUZ (DM)", 9, 15),
            Municipio(339, "MONTE BONITO (DM)", 3, 17),
            Municipio(340, "DOÑA EMMA BALAGUER VDA. VALLEJO(DM)", 3, 10),
            Municipio(341, "LAS CLAVELLINAS (DM)", 3, 10),
            Municipio(342, "VILLA MAGANTE (DM)", 10, 61),
            Municipio(343, "PARADERO (DM)", 30, 33),
            Municipio(344, "EL AGUACATE (DM)", 7, 119),
            Municipio(345, "BARRAQUITO (DM)", 7, 58),
            Municipio(346, "RIO GRANDE (DM)", 21, 39),
            Municipio(347, "ARROYO AL MEDIO (DM)", 15, 71),
            Municipio(348, "JORGILLO (DM)", 25, 108),
            Municipio(349, "MANABAO (DM)", 14, 50),
            Municipio(350, "PUÑAL", 28, 350),
            Municipio(351, "GUAYABAL (DM)", 28, 350),
            Municipio(352, "CANABACOA ABAJO (DM)", 28, 350),
            Municipio(353, "LAS MAGUANAS-HATO NUEVO (DM)", 25, 12),
            Municipio(354, "ARROYO TORO-MASIPEDRO (DM)", 16, 48),
            Municipio(355, "LAS LOMAS (DM)", 3, 10),
            Municipio(356, "LA GUAZARA (DM)", 5, 18),
            Municipio(357, "LAS CHARCAS DE MARIA NOVAS (DM)", 25, 12),
            Municipio(358, "EL LIMONAL (DM)", 20, 3),
            Municipio(359, "GUAYABO (DM)", 9, 16),
            Municipio(360, "LA CALETA (DM)", 13, 26),
            Municipio(361, "SABANA HIGUERO (DM)", 9, 15),
            Municipio(362, "JAYACO (DM)", 16, 48),
            Municipio(363, "JAYA (DM)", 7, 56),
            Municipio(364, "GUAYACANES", 26, 364),
            Municipio(365, "SANTIAGO DE LA CRUZ (DM)", 6, 73),
            Municipio(366, "JINOVA (DM)", 25, 129),
            Municipio(367, "SAN FRANCISCO-VICENTILLO (DM)", 8, 25),
            Municipio(368, "RANCHO DE LA GUARDIA (DM)", 9, 75),
            Municipio(369, "CABALLERO (DM)", 27, 49),
            Municipio(370, "LOS FRIOS (DM)", 3, 17),
            Municipio(371, "MENA (DM)", 4, 76),
            Municipio(372, "MAIMON (DM)", 21, 37),
            Municipio(373, "COMEDERO ARRIBA (DM)", 27, 49),
            Municipio(374, "CANCA LA PIEDRA (DM)", 28, 32),
            Municipio(375, "ESTRECHO DE LUPERON OMAR BROSS (DM)", 21, 40),
            Municipio(376, "DON ANTONIO GUZMAN FERNANDEZ (DM)", 7, 56),
            Municipio(377, "HATO NUEVO CORTES (DM)", 3, 125),
            Municipio(378, "SANTA LUCIA-LA HIGUERA (DM)", 8, 25),
            Municipio(379, "BATEY 8 (DM)", 12, 128),
            Municipio(380, "PROYECTO 2-C (DM)", 3, 107),
            Municipio(381, "LAS BARIAS (DM)", 20, 3),
            Municipio(382, "EL NARANJAL (DM)", 31, 13),
            Municipio(383, "LA SALVIA-LOS QUEMADOS (DM)", 16, 48),
            Municipio(384, "HERNANDO ALONZO (DM)", 27, 155),
            Municipio(385, "TURISTICO VERON PUNTA CANA (DM)", 2, 28),
            Municipio(386, "SANTA BARBARA EL 6 (DM)", 4, 76),
            Municipio(387, "EL SALADO (DM)", 4, 113),
            Municipio(388, "GUALETE (DM)", 21, 121),
            Municipio(389, "VILLA CENTRAL (DM)", 5, 18),
            Municipio(390, "LA ZANJA (DM)", 25, 12),
            Municipio(391, "MAMÁ TINGO (DM)", 18, 5),
            Municipio(392, "TAVERA (DM)", 14, 47),
            Municipio(393, "ZAMBRANA ABAJO (DM)", 27, 49),
            Municipio(394, "DON JUAN RODRIGUEZ - BARRANCA (DM)", 14, 47),
            Municipio(395, "SANTA MARIA (DM)", 17, 86),
            Municipio(396, "DOÑA ANA (DM)", 24, 82),
            Municipio(397, "HATILLO (DM)", 24, 2),
            Municipio(398, "QUITA SUEÑO (DM)", 24, 93),
            Municipio(399, "SANTIAGO OESTE-CIENFUEGOS (DM)", 28, 31)
        )

        // Variable para que almacenen el id de la provincia y el municipio que seleccionen
        var provinciaId = 0
        var municipioId = 0


        // Rellenando dropdown de provincias y municipios.
        val adapterItemProvincia = ArrayAdapter(this, R.layout.list_item, Provincias.map { it.Descripcion })
        autoCompleteTextViewProvincia.setAdapter(adapterItemProvincia)
        autoCompleteTextViewProvincia.setOnItemClickListener(AdapterView.OnItemClickListener{ adapterView, view, i, l ->
            val item = Provincias[i]
            Toast.makeText(this@MainActivity, "Provincia: ${item.Descripcion}", Toast.LENGTH_SHORT).show()
            provinciaId = item.ID

            if(provinciaId > 0) {
                val municipiosXProvincia: List<Municipio> = municipios.filter {
                    it.IDProvincia == provinciaId
                }


                val adapterItemMunicipio = ArrayAdapter(this, R.layout.list_item, municipiosXProvincia.map { it.Descripcion })
                autoCompleteTextViewMunicipio.setAdapter(adapterItemMunicipio)

                autoCompleteTextViewMunicipio.setOnItemClickListener(AdapterView.OnItemClickListener{ adapterView, view, i, l ->
                    val item = municipiosXProvincia[i]
                    Toast.makeText(this@MainActivity, "Item: ${item.Descripcion}", Toast.LENGTH_SHORT).show()
                    municipioId = item.ID
                })
            }
        })

        val contexto: Context = this
        buttonGuardar.setOnClickListener {
            if(validarCamposRequeridos(autoCompleteTextViewProvincia.text.toString()) && validarCamposRequeridos(autoCompleteTextViewMunicipio.text.toString()) && validarCamposRequeridos(autoCompleteTextViewDistrito.text.toString()) && validarCamposRequeridos(autoCompleteTextViewFecha.text.toString()) && validarCamposRequeridos(autoCompleteTextViewCoordinador.text.toString()) && validarCamposRequeridos(autoCompleteTextViewEditor.text.toString())) {
                registrar(contexto, "db_jornada.txt", convertirEnCadena(provinciaId, municipioId, autoCompleteTextViewDistrito.text.toString(), autoCompleteTextViewSector.text.toString(), autoCompleteTextViewFecha.text.toString(), autoCompleteTextViewCoordinador.text.toString(), autoCompleteTextViewEditor.text.toString(), autoCompleteTextViewHogares.text.toString().toInt(), autoCompleteTextViewMujeresH.text.toString().toInt(), autoCompleteTextViewHombresH.text.toString().toInt(), autoCompleteTextViewComerciales.text.toString().toInt(), autoCompleteTextViewImpactadasC.text.toString().toInt(), autoCompleteTextViewEducativos.text.toString().toInt(), autoCompleteTextViewImpactadasE.text.toString().toInt(), autoCompleteTextViewOtros.text.toString().toInt(), autoCompleteTextViewImpactadasO.text.toString().toInt(), autoCompleteTextViewTalleres.text.toString().toInt(), autoCompleteTextViewMujeresT.text.toString().toInt(), autoCompleteTextViewHombresT.text.toString().toInt(), 0))
                registrar(contexto, "backup_jornada.txt", convertirEnCadena(provinciaId, municipioId, autoCompleteTextViewDistrito.text.toString(), autoCompleteTextViewSector.text.toString(), autoCompleteTextViewFecha.text.toString(), autoCompleteTextViewCoordinador.text.toString(), autoCompleteTextViewEditor.text.toString(), autoCompleteTextViewHogares.text.toString().toInt(), autoCompleteTextViewMujeresH.text.toString().toInt(), autoCompleteTextViewHombresH.text.toString().toInt(), autoCompleteTextViewComerciales.text.toString().toInt(), autoCompleteTextViewImpactadasC.text.toString().toInt(), autoCompleteTextViewEducativos.text.toString().toInt(), autoCompleteTextViewImpactadasE.text.toString().toInt(), autoCompleteTextViewOtros.text.toString().toInt(), autoCompleteTextViewImpactadasO.text.toString().toInt(), autoCompleteTextViewTalleres.text.toString().toInt(), autoCompleteTextViewMujeresT.text.toString().toInt(), autoCompleteTextViewHombresT.text.toString().toInt(), 0))
                limpiarCampos(autoCompleteTextViewProvincia, autoCompleteTextViewMunicipio, autoCompleteTextViewDistrito, autoCompleteTextViewSector, autoCompleteTextViewFecha, autoCompleteTextViewCoordinador, autoCompleteTextViewEditor, autoCompleteTextViewHogares, autoCompleteTextViewMujeresH, autoCompleteTextViewHombresH, autoCompleteTextViewComerciales, autoCompleteTextViewImpactadasC, autoCompleteTextViewEducativos, autoCompleteTextViewImpactadasE, autoCompleteTextViewOtros, autoCompleteTextViewImpactadasO, autoCompleteTextViewTalleres, autoCompleteTextViewMujeresT, autoCompleteTextViewHombresT)

            } else {
                Toast.makeText(this@MainActivity, "Alguno de los campos requeridos estan vacios!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonSubir.setOnClickListener {
            subirRegistros("data/data/com.mmujer.jornada/files/db_jornada.txt")
        }
        buttonFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                    // Manejar la fecha seleccionada según tus necesidades
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    // Puedes mostrar la fecha en un Toast o realizar otras acciones
                    // Toast.makeText(this, "Fecha seleccionada: $selectedDate", Toast.LENGTH_SHORT).show()

                    val textoEditable: Editable = Editable.Factory.getInstance().newEditable(selectedDate)
                    autoCompleteTextViewFecha.text = textoEditable
                },
                year,
                month,
                day
            )

            // Configurar una fecha mínima y máxima (opcional)
            // datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            // datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + 1000

            // Mostrar el DatePickerDialog
            datePickerDialog.show()

        }

    }

    fun escribirArchivoTexto(contexto: Context, nombreArchivo: String, contenido: String) {
        try {
            // Obtener la ruta del directorio de archivos privados de la aplicación
            val directorio = contexto.filesDir

            // Crear un objeto File apuntando al archivo en el directorio
            val archivo = File(directorio, nombreArchivo)

            // Crear un flujo de salida para escribir en el archivo
            val flujoSalida = FileOutputStream(archivo)
            val escritor = OutputStreamWriter(flujoSalida)

            // Escribir el contenido en el archivo
            escritor.write(contenido)

            // Cerrar los flujos
            escritor.close()
            flujoSalida.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun agregarTextoArchivo(contexto: Context, nombreArchivo: String, contenido: String) {
        try {
            // Obtener la ruta del directorio de archivos privados de la aplicación
            val directorio = contexto.filesDir

            // Crear un objeto File apuntando al archivo en el directorio
            val archivo = File(directorio, nombreArchivo)

            // Crear un flujo de salida en modo de apertura (append)
            val flujoSalida = FileOutputStream(archivo, true)
            val escritor = OutputStreamWriter(flujoSalida)

            // Agregar el contenido al final del archivo
            escritor.append("\n$contenido")

            // Cerrar los flujos
            escritor.close()
            flujoSalida.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun convertirEnCadena(provincia: Int, municipio: Int, distrito: String, sector: String, fecha: String, coordinador: String, editor: String, hogares: Int, mujeresH: Int, hombresH: Int, comerciales: Int, impactadasC: Int, educativos: Int, impactadosE: Int, otros: Int, impactadosO: Int, talleres: Int, mujeresT: Int, hombresT: Int, totalG: Int) : String{
        val cadena = "$provincia, $municipio, $distrito, $sector, $fecha, $coordinador, $editor, $hogares, $mujeresH, $hombresH, $comerciales, $impactadasC, $educativos, $impactadosE, $otros, $impactadosO, $talleres, $mujeresT, $hombresT, $totalG"
        return cadena
    }

    fun registrar(contexto: Context, nombreArchivo: String, cadena: String){
        val archivo = File("data/data/com.mmujer.jornada/files/$nombreArchivo")
        if(!archivo.exists()) {
            escribirArchivoTexto(contexto, nombreArchivo, cadena)
            Toast.makeText(this@MainActivity, "Registro exitoso!", Toast.LENGTH_SHORT).show()

        } else {
            agregarTextoArchivo(contexto, nombreArchivo, cadena)
            Toast.makeText(this@MainActivity, "Registro exitoso!", Toast.LENGTH_SHORT).show()
        }
    }
    fun leerLineas(rutaArchivo: String): List<String> {
        val lineas = mutableListOf<String>()
        try {
            BufferedReader(FileReader(File(rutaArchivo))).use { reader ->
                var linea: String?
                while (reader.readLine().also { linea = it } != null) {
                    lineas.add(linea!!)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return lineas
    }

    fun construirObjetoDesdeLinea(linea: String): Registro {
        // Aquí debes implementar la lógica específica para parsear tu línea y construir el objeto
        // En este ejemplo, se asume que la línea tiene el formato "campo1, campo2"
        val partes = linea.split(", ")
        val provincia = partes[0].toInt()
        val municipio = partes[1].toInt()
        val distrito = partes[2]
        val sector = partes[3]
        val fecha = partes[4]
        val coordinador = partes[5]
        val editor = partes[6]
        val hogares = partes[7].toInt()
        val mujeresH = partes[8].toInt()
        val hombresH = partes[9].toInt()
        val comerciales = partes[10].toInt()
        val impactadasC = partes[11].toInt()
        val educativos = partes[12].toInt()
        val impactadasE = partes[13].toInt()
        val otros = partes[14].toInt()
        val impactadosO = partes[15].toInt()
        val talleres = partes[16].toInt()
        val mujeresT = partes[17].toInt()
        val hombresT = partes[18].toInt()
        val totalG = 0


        return Registro(provincia, municipio, distrito, sector, fecha, coordinador, editor, hogares, mujeresH, hombresH, comerciales, impactadasC, educativos, impactadasE, otros, impactadosO, talleres, mujeresT, hombresT,totalG)

    }

    fun limpiarArchivo(rutaArchivo: String) {
        try {
            val archivo = File(rutaArchivo)

            // Verifica si el archivo existe antes de intentar limpiarlo
            if (archivo.exists()) {
                // Sobrescribe el archivo con un contenido vacío
                archivo.delete()
            } else {
                println("No hay datos para subir!")
            }
        } catch (e: Exception) {
            println("Error al limpiar el archivo: ${e.message}")
        }
    }

    fun subirRegistroALaNube(registro: Registro){
        val gson = Gson()

        // Convertir el objeto a JSON
        val jsonString = gson.toJson(registro)
        val reg = gson.fromJson(jsonString, Registro::class.java)
        var llamada: Call<Registro> = apiServicio.guardarDatos(reg)

        llamada.enqueue(object : Callback<Registro> {
            override fun onResponse(call: Call<Registro>, response: Response<Registro>) {

                if(response.isSuccessful) {
                    val respuesta: Registro? = response.body()
                    //Toast.makeText(this@MainActivity, "Los datos estan subiendo ${respuesta}", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this@MainActivity, "Fallo: ${response.body()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<Registro>, t: Throwable) {
               //01Toast.makeText(this@MainActivity, "Fallo: $t", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun subirRegistros(rutaArchivo: String){
        val lineas = leerLineas(rutaArchivo)
        val objetos = lineas.map { construirObjetoDesdeLinea(it) }
        if(objetos.isEmpty()) {
            Toast.makeText(this@MainActivity, "No hay datos para subir!", Toast.LENGTH_SHORT).show()
        } else {
            if(isNetworkAvailable()) {
                objetos.map { subirRegistroALaNube(it) }
                limpiarArchivo("data/data/com.mmujer.jornada/files/db_jornada.txt")
                Toast.makeText(this@MainActivity, "Datos subidos exitosamente!!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Debes estar conectado a la red!!!", Toast.LENGTH_SHORT).show()
            }


        }


    }

    fun limpiarCampos(provincia: AutoCompleteTextView, municipio: AutoCompleteTextView, distrito: AutoCompleteTextView, sector: AutoCompleteTextView, fecha: AutoCompleteTextView, coordinador: AutoCompleteTextView, editor: AutoCompleteTextView, hogares: AutoCompleteTextView, mujeresH: AutoCompleteTextView, hombresH: AutoCompleteTextView, comerciales: AutoCompleteTextView, impactadasC: AutoCompleteTextView, educativos: AutoCompleteTextView, impactadosE: AutoCompleteTextView, otros: AutoCompleteTextView, impactadosO: AutoCompleteTextView, talleres: AutoCompleteTextView, mujeresT: AutoCompleteTextView, hombresT: AutoCompleteTextView){
        val dis = ""
        val disEditable: Editable = Editable.Factory.getInstance().newEditable(dis)
        val hog = "0"
        val hogEditable: Editable = Editable.Factory.getInstance().newEditable(hog)

        provincia.text = disEditable
        municipio.text = disEditable
        distrito.text = disEditable
        sector.text = disEditable
        fecha.text = disEditable
        coordinador.text = disEditable
        editor.text = disEditable
        hogares.text = hogEditable
        mujeresH.text = hogEditable
        hombresH.text = hogEditable
        comerciales.text = hogEditable
        impactadasC.text = hogEditable
        educativos.text = hogEditable
        impactadosE.text = hogEditable
        otros.text = hogEditable
        impactadosO.text = hogEditable
        talleres.text = hogEditable
        mujeresT.text = hogEditable
        hombresT.text = hogEditable

    }
    fun validarCamposRequeridos(campo: String) : Boolean {
         if(!campo.equals("") && !campo.equals("0")) {
             return true
         } else {
             return false
         }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting == true
        }
    }
}