package org.ucb.dentatrack.Odontogram.Data.DataSource


import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.ucb.dentatrack.Odontogram.Data.Dto.ToothFirestoreDto

class OdontogramFirestoreDataSource {

    private val firestore = Firebase.firestore

    suspend fun probarConexion(): ToothFirestoreDto {

        val auth = Firebase.auth

        val uid = auth.currentUser?.uid
            ?: auth.signInAnonymously().user?.uid
            ?: error("No se pudo autenticar en Firebase")

        val documento = firestore
            .collection("pruebas_odontograma")
            .document(uid)
            .collection("dientes")
            .document("17")
        println("DentaFirestore UID: $uid")
        val diente = ToothFirestoreDto(
            number = 17,
            status = "OBSERVATION",
            treatment = "Prueba Firestore"
        )

        // Guardar
        documento.set(diente)

        // Leer
        return documento.get()
            .data(ToothFirestoreDto.serializer())

    }
    suspend fun subirOdontogramaPrueba(
        localUserId: Int,
        dientes: List<ToothFirestoreDto>
    ): Int {

        val auth = Firebase.auth

        val uid = auth.currentUser?.uid
            ?: auth.signInAnonymously().user?.uid
            ?: error("No se pudo autenticar")

        val coleccion = firestore
            .collection("pruebas_odontograma")
            .document(uid)
            .collection("usuarios")
            .document(localUserId.toString())
            .collection("dientes")

        dientes.forEach { diente ->
            coleccion
                .document(diente.number.toString())
                .set(diente)
        }

        return dientes.size
    }
    suspend fun descargarOdontogramaPrueba(
        localUserId: Int
    ): List<ToothFirestoreDto> {

        val uid = Firebase.auth.currentUser?.uid
            ?: error("No existe sesión de Firebase")

        val documentos = firestore
            .collection("pruebas_odontograma")
            .document(uid)
            .collection("usuarios")
            .document(localUserId.toString())
            .collection("dientes")
            .get()
            .documents

        return documentos.map {
            it.data(ToothFirestoreDto.serializer())
        }
    }
}