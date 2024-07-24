package org.griotold.concert.domain.user

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var userStore: UserStore

    @Test
    fun `유저 조회 시 해당 유저가 없으면 UserException 이 발생한다`() {
        // given
        val userId = 1L

        // when, then
        assertThatThrownBy { userService.getUser(userId) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }
}
