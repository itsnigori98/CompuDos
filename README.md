# Ejecución de JaCoCo

Debemos tener **JDK 21 de Java** para ejecutarlo.  
Luego de eso, asegúrate de que los **tests estén corridos**.

En IntelliJ:
1. Abre la pestaña de **Maven** en la barra derecha.
2. Selecciona tu proyecto (ejemplo: `mSpringBoot1`).
3. Dentro de **Lifecycle**, da clic en **test** para ejecutar las pruebas.

Después, en la terminal de IntelliJ ejecuta el siguiente comando para correr JaCoCo con los tests:

```bash
mvn clean verify
