-- dangx2.tb_admin definition

CREATE TABLE `tb_admin` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `admin_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '아이디',
  `admin_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '비밀번호',
  `admin_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이름',
  `admin_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '연락처',
  `admin_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이메일',
  `admin_type_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '관리자구분(공통코드)',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '회원 가입일시',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '정보 수정일시',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='관리자 관리';

-- dangx2.tb_board definition

CREATE TABLE `tb_board` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '제목',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '내용',
  `file_idx` int DEFAULT NULL COMMENT '첨부파일 고유값',
  `board_type_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '카테고리(공통코드)',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='게시판 관리';

-- dangx2.tb_bongsa definition

CREATE TABLE `tb_bongsa` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `bongsa_date` datetime NOT NULL COMMENT '활동 신청일시',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'Y' COMMENT '사용여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='봉사활동신청 관리';

-- dangx2.tb_common_code definition

CREATE TABLE `tb_common_code` (
  `group_cd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '그룹코드',
  `group_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '그룹명',
  `cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '코드',
  `cd_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '코드명',
  `cd_order` int NOT NULL COMMENT '정렬 순서',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  PRIMARY KEY (`group_cd`,`cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='공통코드 관리';

-- dangx2.tb_file definition

CREATE TABLE `tb_file` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `save_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '파일 저장명',
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '파일 원본명',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '저장 경로',
  `file_order` int NOT NULL COMMENT '첨부 순번',
  `target_type_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '참조 타입(공통코드)',
  `target_idx` int NOT NULL COMMENT '참조 고유값',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '삭제 여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='파일 관리';

-- dangx2.tb_idle definition

CREATE TABLE `tb_idle` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `idle_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이름',
  `idle_breed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '품종',
  `idle_year` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '생년',
  `idle_gender_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '성별(공통코드)',
  `idle_neuter_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '중성화여부',
  `idle_kg` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '체중',
  `idle_rescue_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '구조당시 내용',
  `idle_current_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '현재상황 내용',
  `idle_type_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '카테고리(공통코드)',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='아이들 정보 관리';

-- dangx2.tb_user definition

CREATE TABLE `tb_user` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '아이디',
  `user_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '비밀번호',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이름',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '연락처',
  `user_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이메일',
  `user_post` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '우편번호',
  `user_addr` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '기본주소',
  `user_addr_dtl` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '상세주소',
  `user_gender_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '회원성별(공통코드)',
  `user_status_cd` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '회원등급(공통코드)',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '회원 가입일시',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '정보 수정일시',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  `user_agree_yn_1` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '정보수집여부 1',
  `user_agree_yn_2` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '정보수집여부 2',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회원 관리';

-- dangx2.tb_board_reply definition

CREATE TABLE `tb_board_reply` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `board_idx` int NOT NULL COMMENT '게시판 고유값',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '내용',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`),
  KEY `board_idx` (`board_idx`),
  CONSTRAINT `tb_board_reply_ibfk_1` FOREIGN KEY (`board_idx`) REFERENCES `tb_board` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='게시판 댓글 관리';

-- dangx2.tb_bongsa_info definition

CREATE TABLE `tb_bongsa_info` (
  `bongsa_idx` int NOT NULL COMMENT '봉사신청 고유값',
  `bongsa_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '이름',
  `bongsa_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '연락처',
  `bongsa_order` int NOT NULL COMMENT '정렬순서',
  PRIMARY KEY (`bongsa_idx`,`bongsa_name`,`bongsa_phone`),
  CONSTRAINT `tb_bongsa_info_ibfk_1` FOREIGN KEY (`bongsa_idx`) REFERENCES `tb_bongsa` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='봉사활동신청 구성원정보 관리';

-- dangx2.tb_idle_reply definition

CREATE TABLE `tb_idle_reply` (
  `idx` int NOT NULL AUTO_INCREMENT COMMENT '고유값',
  `idle_idx` int NOT NULL COMMENT '아이들 고유값',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '내용',
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Y' COMMENT '사용여부',
  `reg_idx` int NOT NULL COMMENT '등록자 고유값',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `mod_idx` int NOT NULL COMMENT '수정자 고유값',
  `mod_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`idx`),
  KEY `idle_idx` (`idle_idx`),
  CONSTRAINT `tb_idle_reply_ibfk_1` FOREIGN KEY (`idle_idx`) REFERENCES `tb_idle` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='아이들 댓글 관리';
