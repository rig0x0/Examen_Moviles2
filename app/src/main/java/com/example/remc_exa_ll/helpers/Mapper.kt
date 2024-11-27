import com.example.remc_exa_ll.data.ApiAddress
import com.example.remc_exa_ll.data.ApiCompany
import com.example.remc_exa_ll.data.ApiGeo
import com.example.remc_exa_ll.data.ApiUser
import com.example.remc_exa_ll.entity.UserEntity

fun List<UserEntity>.toApiUsers(): List<ApiUser> {
    return this.map { userEntity ->
        ApiUser(
            id = userEntity.id,
            name = userEntity.name,
            username = userEntity.username,
            email = userEntity.email,
            address = ApiAddress(
                city = userEntity.address.city,
                street = userEntity.address.street,
                suite = userEntity.address.suite,
                zipcode = userEntity.address.zipcode,
                geo = ApiGeo(
                    lat = userEntity.address.geo.lat,
                    lng = userEntity.address.geo.lng
                )
            ),
            company = ApiCompany(
                name = userEntity.company.name,
                catchPhrase = userEntity.company.catchPhrase,
                bs = userEntity.company.bs
            )
        )
    }
}