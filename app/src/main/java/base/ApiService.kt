package base

import java.lang.ClassCastException
import java.lang.Exception

class ApiService {
    companion object{
        private val map:HashMap<Class<*>,Api> = HashMap()
        fun register(clazz:Class<*>,api:Api){
            if(map.containsKey(clazz)){
                return
            }
            val obj = clazz.newInstance()
            map[clazz] = clazz.newInstance() as Api

        }

        fun of(clazz:Class<*>) : Api?{

           return map[clazz]
        }
    }

}