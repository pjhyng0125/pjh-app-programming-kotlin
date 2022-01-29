package com.pjh.lotto

import java.text.SimpleDateFormat
import java.util.*

object LottoNumberMaker {
    /**
    * 1~45 랜덤 번호 반환
    */
    fun getRandomLottoNumber(): Int {
        return Random().nextInt(45) + 1
    }

    /**
     * 6개 랜덤 번호 반환
     */
    fun getRandomLottoNumbers() :MutableList<Int> {
        val lottoNumbers = mutableListOf<Int>()
        for (i in 1..6) {
            var num = 0
            do {
                num = getRandomLottoNumber()
            } while (lottoNumbers.contains(num))
            lottoNumbers.add(num)
        }
        return lottoNumbers
    }

    /**
     * Shuffle을 사용한 랜덤 번호 6개 생성
     * 1~45 번호 섞은 후 앞 6개 추출
     * 중복 추출 방지
     */
    fun getShuffleLottoNumbers(): MutableList<Int> {
        val list = mutableListOf<Int>()
        for (num in 1..45) {
            list.add(num)
        }
        list.shuffle()

        return list.subList(0, 6)
    }

    /**
     * 입력받은 이름에 대한 hash code를 사용하여 로또 번호를 섞고 결과 반환
     */
    fun getLottoNumbersFromHash(name: String): MutableList<Int> {
        // 1~45 번 로또 번호 저장 리스트 선언
        val list = mutableListOf<Int>()
        // 1~45 반복하며 리스트에 로또 번호 저장
        for (num in 1..45) {
            list.add(num)
        }

        // 날짜 시간값 + name (2022-01-29-jinbro)
        val targetStr = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date()) + name

        // Random(SEED) 활용하여 섞기
        list.shuffle(Random(targetStr.hashCode().toLong()))
        return list.subList(0, 6)
    }
}